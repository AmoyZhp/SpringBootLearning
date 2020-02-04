alter table USER alter column ID BIGINT auto_increment;
alter table QUESTION alter column ID BIGINT auto_increment;
alter table QUESTION alter column CREATOR_ID BIGINT;
alter table COMMENT alter column QUESTION_ID bigint not null;
alter table COMMENT alter column COMMENTOR_ID bigint not null;
alter table COMMENT
	add comment_to_id bigint;
alter table COMMENT
	add type int;