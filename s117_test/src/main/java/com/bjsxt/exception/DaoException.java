package com.bjsxt.exception;

/**
 * 自定义数据访问层异常。
 * 如果Mapper代码抛出异常，Service需要捕获处理，并封装成此异常，抛给控制器。
 */
public class DaoException extends RuntimeException {
    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
