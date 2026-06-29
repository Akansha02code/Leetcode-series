# Write your MySQL query statement below
select query_name , round(avg(rating/position),2) as quality ,
 round(avg(case when rating<3 then 1 else 0 end)*100,2) as poor_query_percentage  #round(if(rating<3 , 1 ,0)*100,2)
from Queries 
group by query_name