/* ���� ���� ���̺� ���� */
CREATE TABLE MONSTERS(
    M_NAME VARCHAR2(30) PRIMARY KEY,
    M_HP NUMBER(3) NOT NULL,
    M_ATK NUMBER(3) NOT NULL,
    M_DEF NUMBER(3) NOT NULL,
    M_EXP NUMBER(6) NOT NULL,
    M_DROPLIST VARCHAR2(100) NOT NULL);

/* ���� ���� ���̺� ������ ����*/
INSERT INTO MONSTERS VALUES('�ذ�', 20, 10, 5, 50, 'ü�� ����,���� ����,���� ��,���� �尩,���� �Ź�');
INSERT INTO MONSTERS VALUES('���', 30, 15, 8, 70, 'ü�� ����,���� ����,���� ����,���� ����,ª�� ��');
INSERT INTO MONSTERS VALUES('������', 30, 18, 10, 100, 'ü�� ����,���� ����,���� ���, ��ø�� ���, ������ ���');
INSERT INTO MONSTERS VALUES('��ũ', 40, 25,15,200, '��ö ��,��ö ����,��ö �Ź�,ü�� ����, ���� ����');
INSERT INTO MONSTERS VALUES('�ں�Ʈ',35,20,15,150,'ü�� ����,���� ����,��ö ����,��ö �尩');
INSERT INTO MONSTERS VALUES('��г��ۻ���',999,999,999,9999,'������ ��,������ ����');

SELECT * FROM MONSTERS;

/* ����ġ ���̺� */
CREATE TABLE EXPTABLE(
    LV NUMBER(2) PRIMARY KEY,
    SUMEXP NUMBER(6) NOT NULL,
    NEXTEXP NUMBER(6) NOT NULL);
    
    /*1 ~ 10 ���� ����ġ ���̺� */
    DECLARE
        CNT NUMBER(2) := 0;
        CNT2 NUMBER(2) :=0;
        SUMEXP NUMBER(6):=0;
        NEXTEXP NUMBER(6) :=0;
    BEGIN
        LOOP
        EXIT WHEN CNT = 10;
            CNT := CNT+1; /* 1 ~ 10 */
            CNT2 := CNT2+1; /* 1 ~ 10 */
            NEXTEXP := CNT2*50;
            SUMEXP := NEXTEXP - 50;
            INSERT INTO EXPTABLE VALUES(CNT, SUMEXP, NEXTEXP);
        END LOOP;
    END;
    
    /* 11 ~ 20 ����ġ ���̺�*/
     DECLARE
        CNT NUMBER(2) := 10;
        CNT2 NUMBER(2) :=0;
        SUMEXP NUMBER(6):=0;
        NEXTEXP NUMBER(6) :=0;
    BEGIN
        LOOP
        EXIT WHEN CNT = 20;
            CNT := CNT+1; /* 11 ~ 20 */
            CNT2 := CNT2+1; /* 1 ~ 10 */
            NEXTEXP := (CNT2*100)+500;
            SUMEXP := NEXTEXP - 100;
            INSERT INTO EXPTABLE VALUES(CNT, SUMEXP, NEXTEXP);
        END LOOP;
    END;
    
    /* 21 ~ 30 ����ġ ���̺�*/
     DECLARE
        CNT NUMBER(2) := 20;
        CNT2 NUMBER(2) :=0;
        SUMEXP NUMBER(6):=0;
        NEXTEXP NUMBER(6) :=0;
    BEGIN
        LOOP
        EXIT WHEN CNT = 30;
            CNT := CNT+1; /* 21 ~ 30 */
            CNT2 := CNT2+1; /* 1 ~ 10 */
            NEXTEXP := (CNT2*200)+1500;
            SUMEXP := NEXTEXP - 200;
            INSERT INTO EXPTABLE VALUES(CNT, SUMEXP, NEXTEXP);
        END LOOP;
    END;
    
SELECT * FROM EXPTABLE;

COMMIT;


    
    