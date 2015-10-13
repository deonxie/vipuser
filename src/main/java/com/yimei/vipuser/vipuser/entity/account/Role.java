package com.yimei.vipuser.vipuser.entity.account;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.yimei.vipuser.vipuser.entity.IdEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;


@Entity
@Table(name = "vipuser_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends IdEntity {
    private String name;
    private String permissions;

    @NotBlank
    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotBlank
    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }


    @Transient
    public List<String> getPermissionList() {
        if (StringUtils.isNotBlank(permissions)) {
            return ImmutableList.copyOf(StringUtils.split(permissions, ","));
        } else {
            return Lists.newArrayList();
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
