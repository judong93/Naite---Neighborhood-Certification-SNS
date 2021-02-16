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
          <label for="nick-input" >닉네임:</label> 
          <input @keypress.enter="changeNick" v-model="changedNick" @keypress.space="checkSpace" 
          maxlength="8" id="nick-input" type="text">
          <button @click="changeNick">변경</button>
          <button @click="closeNickInput" v-show="isNickInputOpen===true">취소</button>
        </form>
      </div>
      <p><span @click="openPwChangingForm">비밀번호 재설정하기</span> </p>
      <p><span @click="openLocationChangingForm">동네 재설정하기</span> </p>
      <p class="delete-account" @click="deleteAccount"><span>회원탈퇴</span></p>
      <p><span @click="backToProfile">내 프로필로 가기</span></p>
    </div>
  </div>
</template>

<script>
import Navbar from '@/components/Basic/Navbar'
import SettingBox from '@/components/Profile/SettingBox'
import jwt_decode from 'jwt-decode'
import axios from 'axios'

const SERVER_URL = 'https://i4a402.p.ssafy.io/api'

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
    setToken:function(){
      const token=localStorage.getItem('jwt')
      const config = {
          headers: {
          'auth-token':`${token}`
          }
      }
      console.log(config)
      return config
    },
    deleteAccount: function () {
      const result = confirm("정말 삭제하시겠습니까?")
      if (result) {
        const config = this.setToken()
        axios.put(`${SERVER_URL}/user/leave`, {}, config)
          .then(() => {
            alert('그동안 나이테를 사용해주셔서 감사합니다')
            localStorage.removeItem('jwt')
            this.$router.push({name:'Sign'})
          })
          .catch((err) => {
            console.log(err)
          })
        }
    },
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
      axios.put(`${SERVER_URL}/user/profile/nick/${this.changedNick}`, {}, this.setToken())
        .then((res) => {
          if (res.data.response==="error") {
            alert(res.data.message)
          } else {
            alert('변경이 완료되었습니다. 다시 로그인하시면 변경사항이 적용됩니다!')
          }
          this.isNickInputOpen = false
          this.changedNick = ''
        })
    },
    closeNickInput: function () {
      this.isNickInputOpen = false
      this.changedNick = ''
    },
    checkSpace: function () {
      event.returnValue=false;
    },
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

@media screen and (max-width: 501px) {
  #profile-settings {
    width: 100vw;
    height: 100vh;
  }
  .setting-title {
    margin-top: 30%;
  }
  .settings-content {
    width: 80%;
  }
  #settingform {
    width: 70%;
  }
}
</style>