package com.appware_system.books.model.entity;

import com.appware_system.books.model.enums.Role;
import com.appware_system.books.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "role")
@Data
@AllArgsConstructor
public class RoleEntity extends BaseEntity{

    @Column(name = "user_role", nullable = false)
    private Boolean userRole;
    @Column(name = "admin_role", nullable = false)
    private Boolean admin;

    public RoleEntity() {
        this.setStatus(Status.ACTIVE);
        this.setCreated(LocalDate.now());
    }

    /**
     * Returns a string representation of the object, indicating the roles associated with the user.
     *
     * @return A string representing the roles of the user.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int t = 0;
        if (this.admin) {
            stringBuilder.append(Role.ADMIN);
            t++;
        }
        if (this.userRole) {
            if (t > 0)
                stringBuilder.append(",");
            stringBuilder.append(Role.USER);
        }
        return stringBuilder.toString();
    }




}
