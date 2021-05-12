import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Jackson {

    public static class User {

        private Integer id;
        private String name;
        private Timestamp birthday;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Timestamp getBirthday() {
            return birthday;
        }

        public void setBirthday(Timestamp birthday) {
            this.birthday = birthday;
        }

        public User(Integer id, String name, Timestamp birthday) {
            super();
            this.id = id;
            this.name = name;
            this.birthday = birthday;
        }

        public User() {
            super();
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("User [id=").append(id).append(", name=").append(name)
                    .append(", birthday=").append(birthday).append("]");
            return builder.toString();
        }

    }

    public static void writeJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:SS"));

        String json = objectMapper.writeValueAsString(new User(999, "Nick Huang", new Timestamp(Calendar.getInstance().getTimeInMillis())));
        System.out.println(json);
    }


    public static void readJson() throws IOException {
        String json = "{\"id\":999,\"name\":\"Nick Huang\",\"birthday\":\"2015-10-21 15:45:673\"}";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:SS"));

        User user = objectMapper.readValue(json, User.class);
        System.out.println(user);
    }

    public static void main(String[] args) {
        try {
            writeJson();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            readJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
