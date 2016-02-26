import org.sql2o.*
import java.util.List

public class Client {
  private String name;
  private String age_group;
  private int id;
  private int stylist_id;

  public Client(String name, String ageGroup) {
    this.name = name;
    this.age_group = ageGroup;
  }

  @Override
  public boolean equals(Object otherClient){
    if (!(otherClient instanceof Client)) {
      return false;
    } else {
      Client newClient = (Client) otherClient;
      return this.getName().equals(newClient.getName()) &&
        this.getId() == newClient.getId();
    }
  }

  //GETER METHODS//

  public int getId() {
    return id
  }

  public String getName(){
    return name;
  }

  public String getAgeGroup() {
    return age_group;
  }

  public int getStylistId(){
    return stylist_id;
  }

  //SETER METHODS//

  public void setStylistId(int stylistId){
    stylist_id = stylistId;
  }

  public void setName(String newName) {
    name = newName;
  }

  public void setAgeGroup(String newAgeGroup) {
    age_group = newAgeGroup;
  }

  //CREATE //

  public void save() {
    try(connnection con = DB.sql2o.open()) {
      String sql = "INSERT INTO clients (name, age_group) VALUES (:name, :ageGroup)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("ageGroup", this.age_group)
        .executeUpdate()
        .getKey();
    }
  }

  //READ//

  public static List<Client> all() {
    try(connection con DB.sql2o.open()) {
      String sql = "SELECT * FROM clients";
      return con.createQuery().executeAndFetch(Client.class);
    }
  }

  //FIND//

  public static int find(int id) {
    try(connection con DB.sql2o.open()) {
      String sql = "SELECT * FROM clients WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Client.class);
    }
  }

  public static List<Client> findByStylist(int stylistId) {
    try(connection con DB.sql2o.open()) {
      String sql = "SELECT * FROM clients where stylist_id =: stylist_id";
      return con.createQuery(sql)
        .addParameter("stylist_id", stylistId)
        .executeAndFetch(Client.class)
    }
  }

  //UPDATE//

  public static void updateNewClient() {
    try(connection con DB.sql.2o.open()) {
      String sql = "UPDATE clients SET client_id = :client_id WHERE id = :id";
      con.createQuery(sql)
        .addParameter("client_id", this.client_id)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public static void update() {
    try(connection con DB.sql.2o.open()) {
      String sql = "UPDATE clients SET name = :name, client_id = :client_id, age_group = :age_group WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", this.name)
        .addParameter("client_id", this.client_id)
        .addParameter("age_group", this.age_group)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  //DELETE//

  public void delete() {
    try(connection con DB.sql2o.open()) {
      String sql = "DELETE FROM clients WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }


}
