# Write your MySQL query statement below
select w1.id from weather w1 join weather w2    #self join to make comparisons 
on datediff(w1.recordDate,w2.recordDate)=1   # date diff should be 1 
where w1.temperature>w2.temperature         # temp should be greater than previous 