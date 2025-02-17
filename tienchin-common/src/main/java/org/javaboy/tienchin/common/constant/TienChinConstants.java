package org.javaboy.tienchin.common.constant;

public interface TienChinConstants {
    int CLUE_TYPE=0;

    int BUSINESS_TYPE=1;

    long NEXT_FOLLOWTIME = 24;

    int CLUE_ALLOCATED = 1;
    int CLUE_FOLLOWING = 2;
    int CLUE_RECOVERY = 3;
    int CLUE_INVALIDATE = 4;

    int BUSINESS_ALLOCATED = 1;
    int BUSINESS_FOLLOWING = 2;
    int BUSINESS_RECOVERY = 3;

    String ADMIN_USERNAME = "admin";
    long ADMIN_ID = 1;
    long ADMIN_DEPT_ID = 103;

    int CONTRACT_UNAPPROVE = 1;
    int CONTRACT_APPROVED = 2;
    int CONTRACT_REJECT = 3;
    String CONTRACT_PROCESS_DEFINITION_ID = "tienchin_contract_approve";
}
