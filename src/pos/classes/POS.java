package pos.classes;

import pos.frames.Login;
import pos.frames.Menu;

public class POS {

    public static void main(String[] args) {
        DBmysqlconnection s = new DBmysqlconnection();
        s.connection();
        
        Login l = new Login(s);
        l.setVisible(true);
        l.setLocationRelativeTo(null);
        
        
//        Menu m = new Menu(s);
//        m.setVisible(true);
//        m.setLocationRelativeTo(null);
    }
    
}
