package com.freewill.admin.common.constant;

/**
 * Redis Key管理类
 * <p>
 * 目的：为了方便Redis-Key的运维管理，集中把Key放入这个类中
 *
 * @author pengqi
 * @date 2016年11月10日 上午10:08:04
 */
public class RedisKeyConst {

    /**
     * 用户动态密码
     */
    public static final String USER_DYNAMIC_PWD = "user_dynamic_pwd_";
    /**
     * 用户可见范围映射
     * key: 'user_scope_' + 用户id
     */
    public static final String LIST_USER_SCOPE = "user_scope_";
    /**
     * Token到ShiroUser映射
     */
    public static final String MAP_LOGIN_TOKEN = "map_login_token_";
    /**
     * 用户id到Token映射
     */
    public static final String MAP_LOGIN_USERID = "map_login_userid_";
    /**
     * 车辆编码  自增长KEY
     */
    public static final String AUTO_CAR_NO = "auto_car_no";
    /**
     * 过户单编码  自增长KEY
     */
    public static final String AUTO_GHD_NO = "auto_ghd_no";
    /**
     * 贷款单编码  自增长KEY
     */
    public static final String AUTO_LOAN_NO = "auto_loan_no";
    /**
     * 保险单编码  自增长KEY
     */
    public static final String AUTO_INSURANCE_NO = "auto_insurance_no";
    /**
     * 延保单编码  自增长KEY
     */
    public static final String AUTO_QUALITY_NO = "auto_quality_no";
    /**
     * 客户编码  自增长KEY
     */
    public static final String AUTO_CUSTOMER_NO = "auto_customer_no";
    /**
     * 合同编码  自增长KEY
     */
    public static final String AUTO_CONTRACT_NO = "auto_contract_no";
    /**
     * 费用报销编码  自增长KEY
     */
    public static final String AUTO_REIMBURSE_NO = "auto_reimburse_no";
    /**
     * 费用财务编码  自增长KEY
     */
    public static final String AUTO_REIMBURSE_FIN_NO = "auto_reimburse_fin_no";
    /**
     * 合同财务编码  自增长KEY
     */
    public static final String AUTO_CONTRACT_FIN_NO = "auto_contract_fin_no";
    /**
     * 整备编码  自增长KEY
     */
    public static final String AUTO_REORGANIZE_NO = "auto_reorganize_no";
    /**
     * 库存编码  自增长KEY
     */
    public static final String AUTO_POSITION_NO = "auto_position_no";
    /**
     * 支款编码
     */
    public static final String AUTO_FIN_PAYMENT_NO = "auto_fin_payment_no";
    /**
     * 支款财务编码(按月生成)
     */
    public static final String AUTO_FIN_PAYMENT_FIN_NO = "auto_fin_payment_fin_no_";
    /**
     * 收款编码
     */
    public static final String AUTO_FIN_RECEIPT_NO = "auto_fin_receipt_no";
    /**
     * 收款财务编码(按月生成)
     */
    public static final String AUTO_FIN_RECEIPT_FIN_NO = "auto_fin_receipt_fin_no_";
    /**
     * 爬取品牌列表标识
     */
    public static final String ROBOT_CAR_BRAND = "robot_car_brand";
    /**
     * 爬取品牌车系列表标识
     */
    public static final String ROBOT_CAR_BRAND_SERIES = "robot_car_brand_";
    /**
     * 爬取车系车型列表标识
     */
    public static final String ROBOT_CAR_SERIES_CATEGORY = "robot_car_series_";
    /**
     * 爬取车型价格信息标识
     */
    public static final String ROBOT_CAR_CATEGORY_PRICE = "robot_car_category_price_";
    /**
     * 公司code标识
     */
    public static final String AUTO_COMPANY__CODE = "auto_company_code_";
    /**
     * 门店code标识
     */
    public static final String AUTO_STORE_CODE = "auto_store_code_";
    /**
     * 检测code标识
     */
    public static final String AUTO_TEST_CODE = "auto_test_code_";

    private RedisKeyConst() {
    }

}
