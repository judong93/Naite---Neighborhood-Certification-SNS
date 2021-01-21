## Backend Daily Report

> 210118 (월)
> ERD 설계

- 게시판 테이블 수정하기
- 메세지 테이블 수정하기
- 메신저 : NoSQL, 서버 하나 새로 만들기



> 210119 (화)
>
> ERD 설계 피드백 후 수정 작업

![image](https://user-images.githubusercontent.com/18069587/105013352-82b3c580-5a82-11eb-8c13-7921c9a389cd.png)



> 210121(목)
> SNS 연동 로그인 시 받아오는 데이터

- 구글

> 이메일, 이름, 프로필 사진, jwt형식의 token과 refresh token

{

  "access_token": "액세스 토큰",

  "expires_in": 3599,

  "refresh_token": "리프레시 토큰",

  "scope": "openid https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email",

  "token_type": "Bearer",

  "id_token": ""

}

{iss=https://accounts.google.com, azp=975805869127-9i4flipgahejer2th4ub052a3q10sup6.apps.googleusercontent.com, aud=975805869127-9i4flipgahejer2th4ub052a3q10sup6.apps.googleusercontent.com, sub=104899002798727274122, email=kimmk0924@gmail.com, email_verified=true, at_hash=dOgK50ncUz5J09h-nn1fIg, name=MinKyung Kim, picture=https://lh3.googleusercontent.com/a-/AOh14GgcNAqVQ0T3uLkyd3wzp8LCpeuNdChI90nw=s96-c, given_name=MinKyung, family_name=Kim, locale=ko, iat=1611195591, exp=1611199191, alg=RS256, kid=eea1b1f42807a8cc136a03a3c16d29db8296daf0, typ=JWT} 


-네이버 

![image](https://files.slack.com/files-pri/T01JDHV1X35-F01KCGGLEN6/image.png)

{

  "birthday":"04-19",

  "profile_image":"https:\/\/ssl.pstatic.net\/static\/pw\/address\/img_profile.png",

  "gender":"M",

  "birthyear":"1995",

  "nickname":"우헹헹하헤",

  "mobile":"010-9502-4019",

  "name":"조성훈",

  "id":"47389938",

  "age":"20-29",

  "email":"sung95419@gmail.com",

  "mobile_e164":"+821095024019"

}
