
# Treasury

Treasury is a simple app for managing your personal savings. It's my first "real" app with GUI. 

### Prerequisites

To build this project, you need to instal NetBeans IDE


## How it works

### User account

At the beggining, you will be asked for your login data. You can both create your own account or type just type "test" as a user name and password. 
If you wanna create your own account, you'll see the following:

![zrzut ekranu 2017-11-16 o 08 39 39](https://user-images.githubusercontent.com/30430556/32880022-7071cd18-caac-11e7-88d0-80c7cf096aa9.png)

The application itn't connected to a server, so it doesn't matter whether typed information is correct or not. You can type whathever you want. 

### Application logic

After creating a new account, you'll be inicially asked for typing your desired finantial goal. If you use "test" account, you can modify your goal in the settings.

Application's main window (treasury room) visualy represents your progress. The progress is calculated as a relationship between your present financial ballance and your goal. 

Therefore, while your ballance is equal to 0, you should see this:

![zrzut ekranu 2017-11-16 o 08 40 42](https://user-images.githubusercontent.com/30430556/32880039-89c97d60-caac-11e7-8226-bae1f97634c4.png)

Then by depositing more money, you should see your treasury room gradually fills with items.

![zrzut ekranu 2017-11-16 o 08 41 18](https://user-images.githubusercontent.com/30430556/32880050-927e6fa6-caac-11e7-8579-3b14914cf9aa.png)


### Settings

Settings allows you to make various changes like: langage switch, setting a new goal, changing a password or delete user account.

## Built With

* JavaFX - GUI
* JDBC/SQLite - database


## Author

* **Mateusz Kalinowski** - [LinkedIn Account](https://www.linkedin.com/in/mateusz-kalinowski-ba1544ba/)
