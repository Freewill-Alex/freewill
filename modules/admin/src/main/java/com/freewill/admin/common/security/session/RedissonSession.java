package com.freewill.admin.common.security.session;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.StoppedSessionException;
import org.apache.shiro.session.mgt.AbstractSessionManager;
import org.redisson.RedissonScript;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisException;
import org.redisson.client.codec.Codec;
import org.redisson.codec.JsonJacksonCodec;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.freewill.admin.common.security.session.RedissonSessionScript.*;

/**
 * <p>A {@link org.apache.shiro.session.Session} implementation backed by Redisson Objects.</p>
 *
 * @author streamone
 */
@Slf4j
public class RedissonSession extends OnlineSession {

    private RedissonClient redisson;

    private Codec infoCodec = new JsonJacksonCodec();
    private Codec codec = infoCodec;
    private String infoKey;
    private String attrKey;

    private Serializable id;

    RedissonSession(RedissonClient redisson, Codec codec, String infoKey, String attrKey,
                    Serializable id) {

        if (redisson == null || infoKey == null || attrKey == null || id == null) {
            throw new IllegalArgumentException("Arguments must not be null!");
        }

        this.redisson = redisson;
        if (codec != null) {
            this.codec = codec;
        }
        this.infoKey = infoKey;
        this.attrKey = attrKey;
        this.id = id;
    }

    RedissonSession(RedissonClient redisson, Codec codec, String infoKey, String attrKey,
                    Session session) {
        if (redisson == null || infoKey == null || attrKey == null || session == null) {
            throw new IllegalArgumentException("Arguments must not be null!");
        }

        if (session.getId() == null) {
            throw new IllegalArgumentException("Session id must not be null!");
        }

        this.redisson = redisson;
        if (codec != null) {
            this.codec = codec;
        }
        this.infoKey = infoKey;
        this.attrKey = attrKey;
        this.id = session.getId();
        init(session);
    }

    private void init(final Session session) {
        final long timeout = session.getTimeout() > 0 ? session.getTimeout() :
                AbstractSessionManager.DEFAULT_GLOBAL_SESSION_TIMEOUT;

        Date startTimeStamp = session.getStartTimestamp();
        startTimeStamp = startTimeStamp != null ? startTimeStamp : new Date();

        String host = session.getHost();
        host = host != null ? host : "";

        List<Object> keys = new ArrayList<>(1);
        keys.add(this.infoKey);

        RedissonScript script = (RedissonScript) this.redisson.getScript();
        script.eval(this.infoKey, RScript.Mode.READ_WRITE, this.infoCodec, INIT_SCRIPT,
                RScript.ReturnType.VALUE, keys, session.getId(), timeout, startTimeStamp,
                host);
    }

    @Override
    public Serializable getId() {
        return this.id;
    }

    @Override
    public Date getStartTimestamp() {
        return getDate(GET_START_SCRIPT);
    }

    private Date getDate(String getStartScript) {
        List<Object> keys = new ArrayList<>(1);
        keys.add(this.infoKey);

        RedissonScript script = (RedissonScript) this.redisson.getScript();
        Date res = null;
        try {
            res = script.eval(this.infoKey, RScript.Mode.READ_ONLY,
                    this.infoCodec, getStartScript,
                    RScript.ReturnType.MAPVALUE, keys);
        } catch (RedisException e) {
            convertException(e);
        }

        if (res == null) {
            throw new InvalidSessionException();
        } else {
            return res;
        }
    }

    @Override
    public Date getLastAccessTime() {
        return getDate(GET_LAST_SCRIPT);
    }

    @Override
    public long getTimeout() throws InvalidSessionException {
        List<Object> keys = new ArrayList<>(1);
        keys.add(this.infoKey);

        RedissonScript script = (RedissonScript) this.redisson.getScript();
        Long res = null;
        try {
            res = script.eval(this.infoKey, RScript.Mode.READ_ONLY,
                    this.infoCodec, GET_TIMEOUT_SCRIPT,
                    RScript.ReturnType.MAPVALUE, keys);
        } catch (RedisException e) {
            convertException(e);
        }

        if (res == null) {
            throw new InvalidSessionException();
        } else {
            return res;
        }
    }

    @Override
    public void setTimeout(long maxIdleTimeInMillis) throws InvalidSessionException {
        List<Object> keys = new ArrayList<>(1);
        keys.add(this.infoKey);
        keys.add(this.attrKey);

        RedissonScript script = (RedissonScript) this.redisson.getScript();
        try {
            script.eval(this.infoKey, RScript.Mode.READ_WRITE,
                    this.infoCodec, SET_TIMEOUT_SCRIPT,
                    RScript.ReturnType.VALUE, keys, maxIdleTimeInMillis);
        } catch (RedisException e) {
            convertException(e);
        }
    }

    @Override
    public String getHost() {
        List<Object> keys = new ArrayList<>(1);
        keys.add(this.infoKey);

        RedissonScript script = (RedissonScript) this.redisson.getScript();
        String res = null;
        try {
            res = script.eval(this.infoKey, RScript.Mode.READ_ONLY,
                    this.infoCodec, GET_HOST_SCRIPT,
                    RScript.ReturnType.MAPVALUE, keys);
        } catch (RedisException e) {
            convertException(e);
        }

        if (res == null) {
            throw new InvalidSessionException();
        } else {
            return res;
        }
    }

    @Override
    public void touch() throws InvalidSessionException {
        List<Object> keys = new ArrayList<>(2);
        keys.add(this.infoKey);
        keys.add(this.attrKey);

        RedissonScript script = (RedissonScript) this.redisson.getScript();
        try {
            script.eval(this.infoKey, RScript.Mode.READ_WRITE, this.infoCodec,
                    TOUCH_SCRIPT, RScript.ReturnType.VALUE, keys, new Date());
        } catch (RedisException e) {
            convertException(e);
        }
    }

    @Override
    public void stop() throws InvalidSessionException {
        List<Object> keys = new ArrayList<>(1);
        keys.add(this.infoKey);

        RedissonScript script = (RedissonScript) this.redisson.getScript();
        try {
            script.eval(this.infoKey, RScript.Mode.READ_WRITE, this.infoCodec,
                    STOP_SCRIPT, RScript.ReturnType.VALUE, keys, new Date());
        } catch (RedisException e) {
            convertException(e);
        }
    }

    @Override
    public Collection<Object> getAttributeKeys() throws InvalidSessionException {
        List<Object> keys = new ArrayList<>(2);
        keys.add(this.infoKey);
        keys.add(this.attrKey);

        RedissonScript script = (RedissonScript) this.redisson.getScript();
        Collection<Object> res = null;
        try {
            res = script.eval(this.infoKey, RScript.Mode.READ_ONLY, this.codec,
                    GET_ATTRKEYS_SCRIPT, RScript.ReturnType.MAPVALUELIST, keys);
        } catch (RedisException e) {
            convertException(e);
        }

        if (res == null) {
            throw new InvalidSessionException();
        } else {
            return res;
        }
    }

    @Override
    public Object getAttribute(Object key) throws InvalidSessionException {
        List<Object> keys = new ArrayList<>(2);
        keys.add(this.infoKey);
        keys.add(this.attrKey);

        RedissonScript script = (RedissonScript) this.redisson.getScript();
        Object res = null;
        try {
            res = script.eval(this.infoKey, RScript.Mode.READ_ONLY, this.codec,
                    GET_ATTR_SCRIPT, RScript.ReturnType.MAPVALUE, keys, key);
        } catch (RedisException e) {
            convertException(e);
        }

        return res;
    }

    @Override
    public void setAttribute(Object key, Object value) throws InvalidSessionException {
        List<Object> keys = new ArrayList<>(2);
        keys.add(this.infoKey);
        keys.add(this.attrKey);

        RedissonScript script = (RedissonScript) this.redisson.getScript();
        try {
            script.eval(this.infoKey, RScript.Mode.READ_WRITE, this.codec,
                    SET_ATTR_SCRIPT, RScript.ReturnType.VALUE, keys, key, value);
        } catch (RedisException e) {
            convertException(e);
        }
    }

    @Override
    public Object removeAttribute(Object key) throws InvalidSessionException {
        List<Object> keys = new ArrayList<>(2);
        keys.add(this.infoKey);
        keys.add(this.attrKey);

        RedissonScript script = (RedissonScript) this.redisson.getScript();
        Object res = null;
        try {
            res = script.eval(this.infoKey, RScript.Mode.READ_WRITE, this.codec,
                    REMOVE_ATTR_SCRIPT, RScript.ReturnType.MAPVALUE, keys, key);
        } catch (RedisException e) {
            convertException(e);
        }

        return res;
    }

    private void convertException(RedisException e) {
        String errMsg = e.getMessage();
        if (RETURN_CODE_EXPIRED.equals(errMsg)) {
            throw new ExpiredSessionException();
        } else if (RETURN_CODE_STOPPED.equals(errMsg)) {
            throw new StoppedSessionException();
        } else if (RETURN_CODE_INVALID.equals(errMsg)) {
            throw new InvalidSessionException();
        } else {
            throw e;
        }
    }

}