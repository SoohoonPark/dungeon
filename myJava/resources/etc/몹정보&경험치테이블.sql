/* 몬스터 정보 테이블 생성 */
CREATE TABLE MONSTERS(
    M_NAME VARCHAR2(30) PRIMARY KEY,
    M_HP NUMBER(3) NOT NULL,
    M_ATK NUMBER(3) NOT NULL,
    M_DEF NUMBER(3) NOT NULL,
    M_EXP NUMBER(6) NOT NULL,
    M_DROPLIST VARCHAR2(100) NOT NULL);

/* 몬스터 정보 테이블에 저장할 값들*/
INSERT INTO MONSTERS VALUES('해골', 20, 10, 5, 50, '체력 물약,마나 물약,낡은 검,가죽 장갑,가죽 신발');
INSERT INTO MONSTERS VALUES('고블린', 30, 15, 8, 70, '체력 물약,마나 물약,가죽 갑옷,가죽 투구,짧은 검');
INSERT INTO MONSTERS VALUES('슬라임', 30, 18, 10, 100, '체력 물약,마나 물약,힘의 비약, 민첩의 비약, 지능의 비약');
INSERT INTO MONSTERS VALUES('오크', 40, 25,15,200, '강철 검,강철 투구,강철 신발,체력 물약, 마나 물약');
INSERT INTO MONSTERS VALUES('코볼트',35,20,15,150,'체력 물약,마나 물약,강철 갑옷,강철 장갑');
INSERT INTO MONSTERS VALUES('기분나쁜생물',999,999,999,9999,'전설의 검,전설의 갑옷');

SELECT * FROM MONSTERS;

/* 경험치 테이블 */
CREATE TABLE EXPTABLE(
    LV NUMBER(2) PRIMARY KEY,
    SUMEXP NUMBER(6) NOT NULL,
    NEXTEXP NUMBER(6) NOT NULL);
    
    /*1 ~ 10 레벨 경험치 테이블 */
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
    
    /* 11 ~ 20 경험치 테이블*/
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
    
    /* 21 ~ 30 경험치 테이블*/
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


    
    