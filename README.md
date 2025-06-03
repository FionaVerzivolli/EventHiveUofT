# **EventHiveUofT** 🎉

A centralized event-management system that connects the University of Toronto community through an interactive map, RSVP tracking, real-time notifications, and inclusive accessibility features.

---

## 👀 Quick Look – Interactive Campus Map
<img src="https://github.com/user-attachments/assets/f83fb559-8f97-4689-8d28-aeeca08c627c" width="600" alt="Interactive Map"/>

<br>
<br>

> **Hover or click pins** to explore events across Robarts, Sidney Smith, and more. Filter by tag, date, or building to discover exactly what you need—fast.

<br>

<img src="https://github.com/user-attachments/assets/cf4fb3ce-2b98-4f4d-b79b-91f571ac4f1a" width="500" alt="Map Filters"/>


---

## **Authors & Contributors** 👥

### Core Team
- **Fiona Verzivolli**  [@FionaVerzivolli](https://github.com/FionaVerzivolli)  
- **Andrew Sasmito**  [@AndrewSasmito](https://github.com/AndrewSasmito)
- **Naoroj Farhan**  [@pirate2580](https://github.com/pirate2580)  
- **Edric N.H.G. Ho**  [@Edric.main(cryingFace)](https://github.com/Edric-Ho)  
- **Presentation Slides:** [Click For Slides!](https://docs.google.com/presentation/d/1HSD1_LwToquN6OsTPC3G4YrxJb72FrwKeaKO-189-bI/edit?usp=sharing)

### Special Thanks
Collaborators at the University of Toronto, including project mentors, peer reviewers, and the teaching team.

---

## **Summary** 📝 <a name="summary"></a>

**EventHiveUofT** solves the problem of fragmented campus-event information by offering a single platform where students and staff can create, discover, and manage events with ease.

**Highlights**

- **Event Creation & Management** with real-time storage via Firebase  
- **Interactive Campus Map** (see hero above) powered by JXMapViewer  
- **RSVP & Email Notifications** so organizers and attendees stay in sync  
- **Accessibility** features such as color-blind-friendly markers and planned screen-reader support  

Our goal is to build a connected campus community where networking, learning, and socializing are effortless. 🚀

---

## **Features** 🚀 <a name="features"></a>

### 1. Interactive Campus Map 🗺️
Visualize events across U of T with clickable pins and building overlays.

**Technical Highlights**
- Built on **JXMapViewer** for smooth pan/zoom  
- `ViewEventInteractor` & `ViewRSVPInteractor` synchronize map state with Firebase  

*(Screenshots shown in the hero section above.)*

---

### 2. Event Creation & Management ✏️
Users can **create, modify, and delete events** with fields for name, organizer, date/time, location (lat 43–44 / lon -78–-79), description, and tags.

**Technical Highlights**
- `CreateEventInteractor` & `ModifyEventInteractor` handle validation and Firebase persistence  
- Data stored via `FirebaseService` for low-latency updates

Create Event:

<img src="https://github.com/user-attachments/assets/1d28d343-db1d-4431-87a6-875d55cf3d7e" width="350" alt="Create Event"/>



Modify Event:

<img src="https://github.com/user-attachments/assets/67d7beec-8611-4723-8b49-c27c82d02e4b" width="350" alt="Modify Event"/>


---

### 3. RSVP & Notifications 📅
Attendees RSVP with one click; organizers track numbers and send updates.

**Technical Highlights**
- `RSVPEventInteractor` manages attendance lists  
- `NotifyUserInteractor` + `EmailSender` trigger confirmation and reminder emails  

RSVP Here:

<img src="https://github.com/user-attachments/assets/5dd10100-4520-4950-a99a-c26d4e8f7da6" width="450" alt="RSVP Flow"/>


View RSVPed events here (in this example, there are none):

<img src="https://github.com/user-attachments/assets/97fd2417-6289-4d66-a80e-9abb36681186" width="450" alt="RSVP List"/>

---

### 4. User Registration & Login 🔐
Secure Firebase-backed accounts with email/password, duplicate checks, and strength validation.

Register Screen:

<img src="https://github.com/user-attachments/assets/471bdcc4-0b19-48d4-af08-0c59ef2a26d7" width="550" alt="Register"/>

Login Screen:

<img src="https://github.com/user-attachments/assets/da53af0b-99f7-4651-9e0b-0a6075a49b9f" width="550" alt="Login"/>

---

### 5. Email Notifications 📧
- **Instant confirmations** – “You’re in!” email sent immediately after RSVP (`NotifyUserInteractor` → `EmailSender`).
- **Update / cancel blasts** – edits or cancellations trigger a single email to all current RSVPs.
- **Template editor** – organizers tweak subject/body (Markdown supported) with no redeploy.
- **Delivery logs** – each outbound email writes a status doc in Firebase for audit & retry.

### 6. Accessibility ♿
- **Zoomable UI & resizable text** for low-vision users.
- **Alt-text on all images** for screen readers.
- **Live error feedback** via ARIA `role="alert"` regions.

*Roadmap:* screen-reader labels on pins, high-contrast theme toggle, full WCAG 2.1 AA audit.


## **License** 📜

This project is licensed under the **MIT License**, which allows for free use, modification, and distribution of the code with proper attribution. For more details about your rights and limitations under this license, see the `LICENSE` file included in this repository.

### **Summary of the License**:

- **Permitted**: Modification,and private use.
- **Conditions**: The code must include attribution to the original authors.
- **Limitations**: There is no warranty, and the contributors are not liable for any damages.

## **Feedback** 💬

We value your feedback to continuously improve **EventHiveUofT**. If you have suggestions, encounter issues, or simply want to provide us with some comments, please use the methods below:

1. **GitHub Issues**:

    - Report bugs or request features directly through our [GitHub Issue Tracker](https://github.com/FionaVerzivolli/EventHiveUofT/issues). Use clear titles and descriptions to help us understand the issue.

2. **Google Form**:

    - Fill out our [Feedback Form](https://docs.google.com/forms/d/e/1FAIpQLSe0zIZ00Kl7o03l7Zc02Nt0jrG6-B35kbchZ-QnLT_0drhsyQ/viewform?usp=pp_url) to share your thoughts, experiences, or suggestions.

**Guidelines for Feedback**:

- **Be Specific**: Describe issues or suggestions clearly, including screenshots if possible.
- **Constructive Language**: Please use respectful and helpful language to foster productive discussions.
- **Response Time**: We aim to respond to feedback **ASAP**.


## **Contribution** 🤝

Contributions are welcome and encouraged! Here’s how you can get involved:

### **How to Contribute**:

1. **Fork the Repository**:

    - Click the **Fork** button at the top of the repository page.
    - This creates a copy of the repository under your GitHub account.

2. **Clone the Fork**:

   ```sh
   git clone https://github.com/FionaVerzivolli/EventHiveUofT.git
   cd EventHiveUofT
   ```

3. **Create a Branch**:

    - Use a descriptive name for your branch:
      ```sh
      git checkout -b new-feature-branch
      ```

4. **Make Changes and Commit**:

    - Implement your changes, ensuring the code is well-documented.
    - Run all tests before committing to make sure nothing is broken.
    - Commit changes:
      ```sh
      git add .
      git commit -m "Add new feature: <description>"
      ```

5. **Push to GitHub**:

   ```sh
   git push origin new-feature-branch
   ```

6. **Create a Pull Request**:

    - Navigate to the original repository and click **New Pull Request**.
    - Fill out the description clearly detailing the changes made.

### **Contribution Guidelines**:

- **Code Style**: Follow our Java coding standards. This helps maintain readability across the entire project.
- **Tests**: Ensure unit tests are added for new features, and all existing tests pass.
- **Documentation**: Update the README and any other relevant documentation to reflect changes.
- **Pull Request Reviews**: Wait for project maintainers to review your changes. Feedback might be provided to make modifications before merging.

**Please read the following PR guideline provided by GitHub**:

- [Best practices for pull requests](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/getting-started/best-practices-for-pull-requests)


### **Development Tips**:

- Use **IntelliJ IDEA** for development and set up **Google Checkstyle** for consistent code quality.
- **Run tests** regularly using:
  ```sh
  mvn test
  ```

## **Maintenance** 🛠️

### **Current Maintenance Plan**:

- **Weekly Updates**: The main contributors will push updates and bug fixes on a regular basis to ensure stability and improvements.
- **Dependency Updates**: All dependencies listed in the `pom.xml` will be reviewed and updated if needed.
- **New Feature Releases**: Major new features will be developed on a separate branch and merged after thorough testing, following the **semantic versioning** guidelines.

**Monitoring Tools**:

- **GitHub Actions**: Automated builds and tests will be used to maintain code quality.
- **Firebase Dashboard**: To monitor real-time data and ensure stability in user authentication and data management.

**Future Maintenance Considerations**:

- **Adding Support for New Features**: Plans to implement user-customizable UI themes to ensure **inclusive usage** by using React.
- **Scaling Application**: If user adoption grows significantly, migrate to a more scalable database and consider incorporating **cloud computing solutions** like **AWS Lambda** for event processing.




## **FAQs and Troubleshooting** 🛠️


### **FAQs**:


**Q: What should I do if I encounter an error during the registration process?**

- **A**: Ensure all form fields are correctly filled out (username, email, password). Check if the email format is valid and the username isn't already taken.


**Q: Why am I unable to see all events on the map?**  

- **A**: Ensure that you have **enabled all event filters**. By default, only certain types of events may be displayed based on your preferences. Check the filters on the sidebar and adjust the event filter.


**Q: Can I delete an event after I have created it?**  

- **A**: Yes, you can delete an event that you’ve created. Go to **Modify Event**, select the **Delete Event** at the bottom, and click on it. 


**Q: Why am I not receiving email notifications?**  

- **A**: Check your email settings to ensure notifications are not being blocked. Additionally:
  - 1. Verify that your **email address** is correct in **Profile Settings**.
  - 2. Check your **spam folder**.


**Q: How do I update event information after it’s been created?**  

- **A**: If you are the event organizer, navigate to **Your Events** from the main menu. Click on **Modify Event** next to the event you wish to modify, make the necessary changes, and save them. The attendees will be notified of any updates automatically.



**Q: Can I use EventHiveUofT if I’m not affiliated with UofT?**  

- **A**: The platform is primarily built for the **University of Toronto** community, but non-affiliated users can still join **public events** if allowed by the event organizer. Please note that some features, like **creating events**, may be restricted to authenticated UofT users.


**Q: Why can't I find certain buildings on the interactive map?**  

- **A**: Ensure that the map is **fully loaded** and that you are zoomed in enough. Some smaller buildings or less significant landmarks may not be visible at a higher zoom level. The list of buildings includes major UofT locations such as **Robarts Library**, **Sidney Smith Hall**, and more.



### **Common Issues and Troubleshooting:**

**Issue: Firebase Connection Fails**

**Solution**:

- **Verify Service Account Path**: Ensure the `firebase-service-account.json` is correctly placed in the `/private/` directory and that its path matches the **GOOGLE_APPLICATION_CREDENTIALS** environment variable.
- **Permission Checks**: Confirm that the credentials have the required permissions within the Firebase project to access **Firestore**, **Authentication**, and other used services.
- **Network Issues**: If you're behind a firewall or VPN, check that the required ports for Firebase are not blocked.

---

**Issue: Maven Build Errors**

**Solution**:

- **Run Clean Build**: Clear previous build artifacts by running:
  ```sh
  mvn clean install
  ```
- **Update Dependencies**: Ensure that all dependency versions are compatible with each other by checking the `pom.xml` file.
- **JDK Issues**: Make sure you are using **Java 11** or higher, and that the Java environment variable (`JAVA_HOME`) points to the correct version.

---

**Issue: Map Pins Not Showing Up**

**Solution**:

- **Dependencies Set Up**: Verify that the project dependencies have been setted up and Maven has built the porject successfully.
- **Map Data Not Loaded**: Check the console logs for errors related to loading the **JXMapViewer** data. Make sure the data endpoints are properly connected.

---

**Issue: Unable to RSVP to Events**

**Solution**:

- **Login Check**: Make sure you are logged in before attempting to RSVP to an event.
- **Event Capacity**: Verify that the event isn’t at full capacity. The RSVP function will not allow users to join if the event has reached its limit.
- **Network Stability**: Ensure your network connection is stable, as RSVP actions require an active connection to Firebase.

## **Future Features** 🚀

- **Mobile App Integration** 📱: Build an Android and iOS version of **EventHiveUofT** to extend accessibility and allow users to RSVP to events on-the-go.
- **Personalized Recommendations** 💡: Use AI-based algorithms to recommend events to users based on their interests and past activity.
- **Screen Reader Compatibility** 🔊: Integrate screen reader support to make the application more accessible to users with visual impairments.
- **User-Defined Event Categories** 📂: Allow users to create custom event categories to better organize and filter the events they are interested in.
- **Push Notifications** 🔔: Include push notifications for important event updates, RSVP reminders, and upcoming deadlines.


## **Contact Information** 📧

For any additional inquiries or support, feel free to contact us via the following channels:

- **Email**: uofteventhive@gmail.com

Thank you for using **EventHiveUofT**! We are excited to have you join our vibrant community and help you discover amazing events on campus. 🙌
