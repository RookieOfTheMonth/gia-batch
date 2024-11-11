package iisi.example.gia.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Table(name = "dept2")
@Entity
public class DeptDO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPTNO", nullable = false, columnDefinition = "NUMBER(3)")
    private Integer deptno;

    @Column(name = "DNAME", columnDefinition = "VARCHAR2(14 CHAR)")
    private String dname;

    @Column(name = "LOC", columnDefinition = "VARCHAR2(13 CHAR)")
    private String loc;

}
