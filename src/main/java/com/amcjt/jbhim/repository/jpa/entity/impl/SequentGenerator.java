package com.amcjt.jbhim.repository.jpa.entity.impl;

import com.amcjt.jbhim.repository.Entity;
import com.amcjt.jbhim.utils.UUIDGenerator;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * @author JBHIM
 * 数据库唯一主键 uuid生成器
 */
public class SequentGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        String id = "";
        if (((Entity) object).getId() != null && !"".equals(((Entity) object).getId())) {
            id = ((Entity) object).getId();
        } else {
            id = UUIDGenerator.INSTANCE.generateUUIDString();
        }
        return id;
    }
}