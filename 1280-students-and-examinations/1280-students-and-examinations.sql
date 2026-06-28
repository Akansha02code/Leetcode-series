# Write your MySQL query statement below
select stu.student_id, stu.student_name, sub.subject_name, count(exa.subject_name) as attended_exams from Students stu cross join Subjects sub left outer join Examinations exa
on sub.subject_name=exa.subject_name and stu.student_id=exa.student_id
group by stu.student_id,stu.student_name,sub.subject_name
order by stu.student_id, sub.subject_name


# cross join creates all possible combinations for students and subjects
#LEFT outer join combinations, with NULLs for no exams, Shows all subjects + exam count 