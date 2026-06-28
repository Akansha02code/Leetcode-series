# Write your MySQL query statement below
select eu.unique_id,e.name from Employees e left join EmployeeUNI eu on e.id=eu.id
  #jo o/p chahiye vo                     #left join representation     #id ke basis pee hogaa same thats why 