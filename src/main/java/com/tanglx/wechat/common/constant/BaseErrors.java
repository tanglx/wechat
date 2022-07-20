package com.tanglx.wechat.common.constant;

/**
 * @Describe 全局错误代码
 * @Author tanglingxiao
 * @Date 2022-05-25
 */
public enum BaseErrors {


    EX_OTHER_CODE(500, "服务端数据异常！"),

    PROJECT_NAME_IS_REQUIRED_ERROR(1000, "项目名称不能为空"),
    WORKFLOW_ASSOCIATED_PROJECT_ERROR(1001, "删除失败，工作流程已关联项目！"),
    PROJECT_PRIMARY_KEY_CANNOT_EMPTY(1002, "项目主键不能为空"),


    ;

    public int code;
    public String message;

    BaseErrors(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取状态码描述
     *
     * @param code 状态码
     * @return 状态码描述，如果没有返回空串
     */
    public static String getLabel(int code) {
        String result = "";
        for (BaseErrors status : BaseErrors.values()) {
            if (status.code == code) {
                result = status.message;
            }
        }
        return result;
    }
}
