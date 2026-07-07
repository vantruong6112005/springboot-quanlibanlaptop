/*
 * @ (#) BaseEntity.java     1.0    7/7/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */
package vantruong.iuh.entity;


/*
 * @description
 * @author:NguyenTruong
 * @date:  7/7/2026
 * @version:    1.0
 */

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
@Getter
@Setter
@MappedSuperclass // BẢN THÂN LỚP CHA KO PHẢI 1 BẢNG CHỈ DÙNG CHUNG CHO LỚP CON
// KO TẠO RA BẢNG RIÊNG CHO LỚP CHA, CÁC THUỘC TÍNH CỦA LỚP CHA SẼ ĐƯỢC KẾ THỪA BỞI CÁC LỚP CON
public abstract class BaseEntity {
    @CreationTimestamp
    @Column(updatable = false)
    protected LocalDateTime createdAt;
    @UpdateTimestamp
    protected LocalDateTime updatedAt;
}
