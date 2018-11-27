package com.freewill.admin.common.security.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.redisson.RedissonScript;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <p>A {@link SessionDAO} implementation backed by Redisson Objects.</p>
 *
 * @author streamone
 */
public class RedissonSessionDao extends AbstractSessionDAO {

    private static final String SESSION_INFO_KEY_PREFIX = "session:info:";
    private static final String SESSION_ATTR_KEY_PREFIX = "session:attr:";

    private RedissonClient redisson;
    private Codec codec;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        String infoKey = getSessionInfoKey(sessionId.toString());
        String attrKey = getSessionAttrKey(sessionId.toString());
        new RedissonSession(this.redisson, this.codec, infoKey, attrKey, session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        String infoKey = getSessionInfoKey(sessionId.toString());
        String attrKey = getSessionAttrKey(sessionId.toString());
        List<Object> keys = new ArrayList<>(1);
        keys.add(infoKey);
        RedissonScript script = (RedissonScript) this.redisson.getScript();
        Long remainTimeToLive = script.eval(infoKey, RScript.Mode.READ_ONLY, this.codec,
                RedissonSessionScript.READ_SCRIPT,
                RScript.ReturnType.INTEGER, keys);
        if (remainTimeToLive > 0) {
            return new RedissonSession(this.redisson, this.codec, infoKey, attrKey, sessionId);
        } else {
            return null;
        }
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        //do nothing, the RedissonSession will update the session in redis directly
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null || "".equals(session.getId())) {
            return;
        }
        Serializable sessionId = session.getId();
        String infoKey = getSessionInfoKey(sessionId.toString());
        String attrKey = getSessionAttrKey(sessionId.toString());
        List<Object> keys = new ArrayList<>(2);
        keys.add(infoKey);
        keys.add(attrKey);

        RedissonScript script = (RedissonScript) this.redisson.getScript();
        script.eval(infoKey, RScript.Mode.READ_WRITE, this.codec,
                RedissonSessionScript.DELETE_SCRIPT,
                RScript.ReturnType.VALUE, keys);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        //for performance reasons, this method should not be called
        return Collections.EMPTY_LIST;
    }

    /**
     * <p>Get key name by session id for binding a RMap to store basic informations of the session.</p>
     * <p>
     * In redis cluster, key hash tags ensure that the binding RMaps of a session are allocated in the
     * same hash slot.
     * <a href="https://redis.io/topics/cluster-spec#keys-hash-tags">https://redis.io/topics/cluster-spec#keys-hash-tags</a>
     * </p>
     *
     * @param sessionId the session id
     * @return key name
     */
    private String getSessionInfoKey(String sessionId) {
        return SESSION_INFO_KEY_PREFIX + "{" + sessionId + "}";
    }

    /**
     * <p>Get key name by session id for binding a RMap to store attributes of the session.</p>
     * <p>
     * In redis cluster, key hash tags ensure that the binding RMaps of a session are allocated in the
     * same hash slot.
     * <a href="https://redis.io/topics/cluster-spec#keys-hash-tags">https://redis.io/topics/cluster-spec#keys-hash-tags</a>
     * </p>
     *
     * @param sessionId the session id
     * @return key name
     */
    private String getSessionAttrKey(String sessionId) {
        return SESSION_ATTR_KEY_PREFIX + "{" + sessionId + "}";
    }

    public RedissonClient getRedisson() {
        return redisson;
    }

    public void setRedisson(RedissonClient redisson) {
        this.redisson = redisson;
    }

    public Codec getCodec() {
        return codec;
    }

    public void setCodec(Codec codec) {
        this.codec = codec;
    }
}