package com.amcjt.jbhim.repository.jpa.entity;

import com.amcjt.jbhim.repository.Entity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @author jbhim
 * @date 2019/4/27/027.
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class EntityImpl implements Entity {
    @Id
    @Column(length = 36)
    @GeneratedValue(generator = "seq-uuid")
    @GenericGenerator(name = "seq-uuid", strategy = "com.amcjt.jbhim.repository.jpa.SequentGenerator")
    private String id;

    @Column
    @CreatedDate
    private Date createTime;

    @Column
    @LastModifiedDate
    private Date updateTime;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.id == null || getClass() != o.getClass()) {
            return false;
        }
        EntityImpl entity = (EntityImpl) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
