package com.example.lombok.model;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * Demonstrates: @Accessors(chain = true)
 *
 * @Accessors(chain = true)  — setters return 'this', enabling fluent chaining:
 *     employee.setName("Alice").setDepartment("Eng").setSalary(90000);
 *
 * @Accessors(fluent = true) — renames getters/setters to use the field name directly:
 *     employee.name("Alice")   instead of  employee.setName("Alice")
 *     employee.name()          instead of  employee.getName()
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private Long id;
    private String name;
    private String department;
    private String role;
    private double salary;
}
