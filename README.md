# GitHub Repository Listing API

## Overview

This API enables consumers to list all GitHub repositories owned by a given username. The repositories returned will exclude forked repositories. The API is designed to adhere to specific acceptance criteria, including responding to different headers and providing detailed information.

## Acceptance Criteria

### Successful Request

As an API consumer, given a GitHub username and header Accept: application/json, the API will return all repositories of that user which are not forks. The response will include:
- Repository Name
- Owner Login
- For each branch, its name and the last commit SHA.

### Invalid GitHub Username
If an invalid or non-existing GitHub username is provided, the API will return a 404 response with the following format:
```
{
  "message": "Requested user not found in GitHub",
  "status": "404 NOT_FOUND"
}
```
### Invalid Header
If a header Accept: application/xml is provided, the API will return a 406 response with the following format:
```
{
  "message": "Only application/json media type is supported",
  "status": "406 NOT_ACCEPTABLE"
}
```
## How to Use
### Required Software
- Java 17 or higher
- Maven
- An IDE (e.g., IntelliJ IDEA)

### Clone the Repository
Clone the project repository to your local machine:

### Build and Run
Navigate to the project directory and run the file: "AtiperaRecruitmentTaskApplication" 

### Send API Request
You can send an API request using any HTTP client, or you can use IntelliJ's built-in HTTP client. Example request are provided in file Requests.http in "AtiperaRecruitmentTask\local" directory.

### Request format in IntelliJ .http file
```
GET localhost:8080/api/v3/github/users/{username}/repos
Accept: application/json
```
Replace {username} with the GitHub username whose repositories you'd like to list.


### Sample Response
Here's what a sample API response will look like:

```
[
    {
     "name": "RepositoryName",
     "owner": "OwnerUsername",
     "branches": [
                  {
                   "name": "branchName",
                   "commit": "commitSHA"
                   }
                  ]
    }
]
```
### Contact Information
For any questions or concerns, you can reach out to:

- Email: przemyslaw.rafal.swiderski@gmail.com
- GitHub: Przemyslaw-Swiderski

## License
This project is licensed under the MIT License.

Feel free to modify and customize the script to fit your specific requirements.

## Responsibility disclaimer
Author of the script do not take any responsibility for any losses made by script and its usage. User uses the sript on own responsibility.
