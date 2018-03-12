CREATE TABLE "HR"."STUDENT"
   (	"id" NUMBER(19,0),
	    "name" VARCHAR2(4000 BYTE)
   ) ;

CREATE SEQUENCE  "HR"."STUDENT_SEQ"  START WITH 1 INCREMENT BY 1 NOMAXVALUE;

CREATE OR REPLACE TRIGGER "HR"."STUDENT_ID_TRG"
before insert on "STUDENT"
for each row
begin
if :new."id" is null then
select STUDENT_seq.nextval into :new."id"
from dual;
end if;
end;


