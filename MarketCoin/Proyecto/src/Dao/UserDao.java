package Dao;

import Entities.User;
import java.util.List;
import java.util.Optional;


//paquetes de entities
import Entities.User;



public interface UserDao {

    public void insert(User user); //LOGEAR USUARIO

    public boolean verify(long cedula);//VERIFICAR QUE NO TENGA OTRA CUENTA Y QUE TENGA CUENTA

    public boolean deleteUser(long cedula);

    public  Optional<User> findByCedula(long cedula);

    public Optional<User> findByEmail(String email);

    public List<User> findAll();

    public boolean validateCredentials(String email, String password);

    public boolean updateUser(User usuario);

    public boolean changePassword(long cedula, String oldPassword, String newPassword);
}
