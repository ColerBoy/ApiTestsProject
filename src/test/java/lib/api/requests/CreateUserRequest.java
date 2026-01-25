package lib.api.requests;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateUserRequest {

    private String email;
    private String password;
    private String username;
    private String firstName;
    private String lastName;

    public static class Builder {
        private String email;
        private String password;
        private String username;
        private String firstName;
        private String lastName;

        public Builder() {
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public CreateUserRequest build() {
            CreateUserRequest request = new CreateUserRequest();
            request.email = this.email;
            request.password = this.password;
            request.username = this.username;
            request.firstName = this.firstName;
            request.lastName = this.lastName;
            return request;
        }
    }

    // Getters and setters for Jackson serialization

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
