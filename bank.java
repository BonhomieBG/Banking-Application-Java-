
public class bank{
    private String name, address, email, phoneNumber;

    public bank (){}
    public bank(String username, String phone, String physicalAddress, String emailAddress){
        name = username;
        phoneNumber = phone;
        address = physicalAddress;
        email = emailAddress;
    }

    public void set(String username, String phone, String physicalAddress, String emailAddress){
        name = username;
        phoneNumber = phone;
        address = physicalAddress;
        email = emailAddress;
    }

    public String getName(){
        return name;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public String getAddress(){
        return address;
    }
    public String getEmail(){
        return email;
    }
    

    @Override
    public String toString(){
        return "Bank Name: " + name + "\n" +
               "Phone Number: " + phoneNumber + "\n" +
               "Address: " + address + "\n" +
               "Email: " + email + "\n";
    }

}