import java.util.List;
import org.sql2o.*;

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

  //GETTER METHODS//

  public int getId() {
    return id;
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

  //SETTER METHODS//

  public void setStylistId(int stylistId){
    stylist_id = stylistId;
  }

  public void setName(String newName) {
    name = newName;
  }

  public void setAgeGroup(String newAgeGroup) {
    age_group = newAgeGroup;
  }

  //CREATE//

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO clients (name, age_group) VALUES (:name, :age_group)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("age_group", this.age_group)
        .executeUpdate()
        .getKey();
      }
    }

    //READ//

    public static List<Client> all() {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM clients";
        return con.createQuery(sql)
        .executeAndFetch(Client.class);
      }
    }

    //FIND//

    public static Client find(int id) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM clients WHERE id=:id";
        return con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetchFirst(Client.class);
      }
    }

    public static List<Client> findByStylist(int stylistId) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM clients where stylist_id=:stylist_id";
        return con.createQuery(sql)
          .addParameter("stylist_id", stylistId)
          .executeAndFetch(Client.class);
      }
    }

    //UPDATE//

    public void updateNewClient() {
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE clients SET stylist_id=:stylist_id WHERE id=:id";
        con.createQuery(sql)
          .addParameter("id", this.id)
          .addParameter("stylist_id", this.stylist_id)
          .executeUpdate();
      }
    }

    public void update() {
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE clients SET name=:name, stylist_id=:stylist_id, age_group=:age_group WHERE id = :id";
        con.createQuery(sql)
        .addParameter("id", this.id)
          .addParameter("name", this.name)
          .addParameter("stylist_id", this.stylist_id)
          .addParameter("age_group", this.age_group)
          .executeUpdate();
      }
    }

    //DELETE//

    public void delete() {
      try(Connection con = DB.sql2o.open()) {
        String sql = "DELETE FROM clients WHERE id=:id";
        con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
      }
    }
  }
