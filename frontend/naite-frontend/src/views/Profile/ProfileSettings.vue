<template>
  <div id="profile-settings">
    <Navbar />
    <SettingBox 
    :formIsOpen='formIsOpen' :formTitle='formTitle' />
    <p class="setting-title">프로필 세팅</p>
    <div class="settings-content">
      <p>ID: {{userId}}</p>
      <div class="nick-container">
        <p v-show="isNickInputOpen===false" @click="openNickInput" 
        class="nickname">닉네임: {{userNick}}</p>
        <form action="#" v-show="isNickInputOpen===true">
          <label for="nick-input" >변경할 닉네임:</label> 
          <input @keypress.enter="changeNick" v-model="changedNick" 
          maxlength="8" id="nick-input" type="text">
          <button @click="changeNick">변경</button>
          <button @click="closeNickInput" v-show="isNickInputOpen===true">취소</button>
        </form>
      </div>
      <p><span @click="openPwChangingForm">비밀번호 재설정하기</span> </p>
      <p><span @click="openLocationChangingForm">동네 재설정하기</span> </p>
      <p class="delete-account"><span>회원탈퇴</span></p>
      <p><span @click="backToProfile">내 프로필로 가기</span></p>
    </div>
  </div>
</template>

<script>
import Navbar from '@/components/Basic/Navbar'
import SettingBox from '@/components/Profile/SettingBox'
import jwt_decode from 'jwt-decode'
// import axios from 'axios'

// const SERVER_URL = 'https://i4a402.p.ssafy.io/api'

export default {
  name: 'ProfileSettings',
  data: function () {
    return {
      formIsOpen: false,
      formTitle: '',
      changedNick: '',
      userId: '',
      userNick: '',
      isNickInputOpen: false,
      userNo: 0,
    }
  },
  components: { 
    Navbar,
    SettingBox
  },
  methods: {
    backToProfile: function () {
      this.$router.push({ name: 'Profile', params:{userNo:this.userNo}})
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
    openPwChangingForm: function () {
      this.formIsOpen = !this.formIsOpen
      this.formTitle = '비밀번호 재설정'
      console.log(this.formIsOpen)
    },
    openLocationChangingForm: function () {
      this.formIsOpen = !this.formIsOpen
      this.formTitle = '동네 재설정'
    },
    openNickInput: function () {
      this.isNickInputOpen = true
    },
    changeNick: function () {
      console.log(this.changedNick)
      this.isNickInputOpen = false
      this.changedNick = ''
    },
    closeNickInput: function () {
      this.isNickInputOpen = false
      this.changedNick = ''
    }
  },
  created: function () {
    const decode = jwt_decode(localStorage.getItem('jwt'))
    this.userId = decode.user.userId
    this.userNo = decode.user.userNo
    this.userNick = decode.user.userNick
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
  width: 40%;
  /* border: 3px solid red; */
  font-size: 25px;
}
.settings-content > *{
  margin-top: 25px;
}
.settings-content span {
  cursor: pointer;
}
.settings-content span:hover {
  font-size: 120%;
  transition-duration: 0.4s; 
}
.settings-content span:not(:hover) {
  transition-duration: 0.4s;
}
.nick-container {
  display: flex;
  justify-content: center;
} 
.nick-container > p {
  cursor: pointer;
}
.nick-container > p:hover {
  font-size: 130%;
  transition-duration: 0.4s;
}
.nick-container > p:not(:hover) {
  transition-duration: 0.4s;
}
.nick-container input {
  background-color: rgb(250, 246, 240);
  width: 40%;
  border-style: dotted;
  border-top: none;
  border-left: none;
  border-right: none;
  margin: 0 10px;
}
.delete-account {
  color: rgb(196, 4, 4);
}
</style>