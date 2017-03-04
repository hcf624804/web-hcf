package com.huangcf.common;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

public interface PlatformTransactionManager {
	 //����һ���Ѿ����������򴴽�һ���µ�����  
    TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException;  
    void commit(TransactionStatus status) throws TransactionException;  
    void rollback(TransactionStatus status) throws TransactionException;  
}
