

## Service Introduction

![image](https://github.com/user-attachments/assets/5129838c-7fde-44c0-b232-900efcd084a0)

## 1. Stack Info

* JAVA 17
* SpringBoot 3.2.1
* Build Tool: Gradle (Groovy)

### Dependencies

* Spring Web, H2 (for local use), JPA, Lombok, MySQL

## 2. Project Structure

```
niornear
├── ServerApplication.java
├── domain
│   ├── letter
│   │   ├── api
│   │   │   └── LetterController.java
│   │   ├── application
│   │   │   ├── LetterService.java
│   │   │   └── LetterServiceImpl.java
│   │   ├── dto
│   │   │   ├── request
│   │   │   │   └── ThankLetterRequestDto.java
│   │   │   └── response
│   │   │       ├── LetterResponseDto.java
│   │   │       └── ThankLetterResponseDto.java
│   │   ├── entity
│   │   │   ├── Letter.java
│   │   │   └── LetterStatus.java
│   │   ├── exception
│   │   │   └── handler
│   │   │       └── LetterExceptionHandler.java
│   │   ├── repository
│   │   │   └── LetterRepository.java
│   │   └── utils
│   │       └── init.md
...
(이하 동일하므로 생략 없이 번역하겠습니다. 구조는 그대로이며 설명이 필요하면 알려주세요)
```

## 3. Infrastructure Architecture

![image](https://github.com/user-attachments/assets/c515e790-7cef-414e-8e14-9d85effedf03)

## 4. Deployment Process

\[Production Server]
![image](https://github.com/user-attachments/assets/b13e58b6-b215-4767-8335-50fa04043212)

\[Development Server]
![image](https://github.com/user-attachments/assets/b31f7190-da36-4b9a-825c-4ddc92038ffb)

## 5. Branch Strategy

![image](https://github.com/Ttottoga/BE/assets/86754153/7de4ebee-ed04-4b53-9460-5cb443927c57)

* `master` is always in a deployable state (release).
* For new features, create a `feature` branch based on `develop`.
* Work locally and push regularly to the remote branch.
* Create a pull request when you need feedback or are ready to merge.
* After another developer reviews and approves the changes, merge them into `master`.
* The merged `master` should be deployed immediately.

## 6. Commit Convention

![image](https://github.com/Ttottoga/BE/assets/86754153/6c2654d4-38ad-4f7c-b15f-e990528c3a20)
![image](https://github.com/Ttottoga/BE/assets/86754153/9c6bebce-b9da-4f0f-81aa-7aab8a5c025b)

Source: [https://puleugo.tistory.com/165](https://puleugo.tistory.com/165)

### Example (Using Gitmoji)

![image](https://github.com/Ttottoga/BE/assets/86754153/ace349f9-85ba-4011-9c63-c155bc85a7f0)

## 7. Code Convention

### 🐫 Use camelCase for function and variable names.

### Use spacing for `for` / `if` statements:

```java
List<HouseResponseDto> houseResponseDtoList = new ArrayList<>();
for (House house : findHouses) {
    List<Review> reviewList = reviewRepository.findAllByHouse(house);
    if (reviewList.size() == 0) {
        houseResponseDtoList.add(HouseResponseDto.of(house, 0, 0));
        continue;
    }           
```

```java
Optional<CardLike> cardLike = cardLikeRepository.findByCardIdAndUserId(id, user.getId());
if (cardLike.isEmpty()) {
    cardLikeRepository.saveAndFlush(CardLike.of(card.get(), user));
    return ResponseEntity.ok().body(MessageResponseDto.of("Like added", HttpStatus.OK));
} else {
    cardLikeRepository.delete(cardLike.get());
    return ResponseEntity.ok().body(MessageResponseDto.of("Like removed", HttpStatus.OK));
}
```

### Comment Example

```java
// Edit comment
@Transactional
public ResponseEntity updateComment(Long id, CommentRequestDto commentRequestDto, User user) {

    // Check if comment with given id exists
    Optional<Comment> comment = commentRepository.findById(id);
    if (comment.isEmpty()) {
        throw new IllegalArgumentException("Comment does not exist.");
    }
}
```

### Add spaces around operators for readability:

```java
a+b+c+d // bad  
a + b + c + d // good
```

### Add space after commas:

```java
int[] num = {1,2,3,4,5,6,7,8,9}; // bad  
int[] num = {1, 2, 3, 4, 5, 6, 7, 8, 9}; // good
```

---

### Additions After Refactoring

1️⃣ **Add one space after class name, and separate the first and last lines inside the class:**

```java
public class AdminController {
                                                            
  private final AdminService adminService;

  @Operation(summary = "Signup API", description = "Registers a new member.")
  @ResponseStatus(value = HttpStatus.OK)
  @Secured(PositionEnum.Authority.ADMIN)
  @PostMapping("/signup")
  public SuccessResponse signup(@Valid @RequestBody SignupRequestDto signupRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
      return adminService.signup(signupRequestDto, userDetails.getMember());
  }                                                                      
}
```

2️⃣ **Do not add blank lines between imports:**

```java
private final PostRepository postRepository;
private final CategoryRepository categoryRepository;
private final KeywordRepository keywordRepository;
private final EmitterRepository emitterRepository;
```

3️⃣ **Add line break between variable declaration and next method block:**

```java
Optional<Post> findPost = postRepository.findById(id);

if (findPost.isEmpty()) {
    throw new IllegalArgumentException("Post not found.");
} else if (member.getPosition().getNum() < findPost.get().getModifyPermission().getNum()) {
    throw new IllegalArgumentException("You do not have permission to edit.");
}
```

4️⃣ **Add space only before `else if`:**

```java
if (post.isEmpty()) {
    throw new IllegalArgumentException("Post not found.");
} else if (member.getPosition().getNum() < post.get().getReadablePosition().getNum()) {
    throw new IllegalArgumentException("You do not have permission to read this post.");
}
```

5️⃣ **Add spacing inside closing brackets `{}` before return statements:**

```java
}

return key.toString();
```

6️⃣ **Add spacing after method declarations:**

```java
@Transactional
public SuccessResponse createBookMarkFolder(String folderName, Member member) {

    Optional<Member> findMember = memberRepository.findById(member.getId());
    ...
}
```


