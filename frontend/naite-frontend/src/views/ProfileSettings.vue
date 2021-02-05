<template>
  <div id="profile-settings">
    <Navbar />
    <p class="setting-title">프로필 세팅</p>
    <div class="settings-content">
      <p>ID: user1</p>
      <p class="nickname">닉네임: user1</p>
      <p>비밀번호 재설정하기</p>
      <p>동네 재설정하기</p>
      <p class="delete-account">회원탈퇴</p>
      <p @click="backToProfile">내 프로필로 가기</p>
    </div>
  </div>
</template>

<script>
import Navbar from '@/components/Basic/Navbar'
// import axios from 'axios'

export default {
  name: 'ProfileSettings',
  components: {
    Navbar,
  },
  methods: {
    backToProfile: function () {
      this.$router.push({ name: 'Profile' })
    },
    // deleteAccount: function () {
    //   axios.put(`http://i4a402.p.ssafy.io:8080/board/delete/${boardNo}`, config)
    //     .then((res) => {
    //       console.log(res)
    //     })
    //     .catch((err) => {
    //       console.log(err)
    //     })
    // },
  }
}
</script>

<style scoped>
#profile-settings {
  position:relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 1918px;
  height: 935px;
  overflow:hidden;
  background-color: rgb(250, 246, 240);
  font-family: font1;
}
.setting-title {
  position: relative;
  font-size: 40px;
  margin-top: 10%;
}
.settings-content {
  display: flex;
  flex-direction: column;
  /* text-align: left; */
  width: 60%;
  /* border: 3px solid red; */
  font-size: 25px;
}
.settings-content > p {
  margin-top: 25px;
  cursor: pointer;
}
.nickname {
  
}
.delete-account {
  color: rgb(196, 4, 4);
}
</style>