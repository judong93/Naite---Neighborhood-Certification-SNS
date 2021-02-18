<template>
  <div id="profile-settings">
    <SettingBox 
    :formIsOpen='formIsOpen' :formTitle='formTitle' @changeImg='changeImg'/>
    <div class="blackboard">
      <div class="settings-content">
        <div class="setting-title">
          <div class="Pin"></div>
          <div>프로필 설정 페이지</div>
          <div class="Pin"></div> 
        </div>
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
        <p><span @click="openProfileImgChangingForm">프로필 이미지 변경하기</span> </p>
        <p><span @click="openPwChangingForm">비밀번호 재설정하기</span> </p>
        <p><span @click="openLocationChangingForm">동네 재설정하기</span> </p>
        <p><span @click="backToProfile">내 프로필로 가기</span></p>
        <p class="delete-account" @click="deleteAccount"><span>회원탈퇴</span></p>
      </div>
    </div>
    
  </div>
</template>

<script>
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
    SettingBox
  },
  methods: {
    changeImg:function(imgUrl){
      this.$emit('changeImg',imgUrl)
    },
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
    },
    openProfileImgChangingForm: function () {
      this.formIsOpen = !this.formIsOpen
      this.formTitle = '프로필 이미지 변경'
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
            this.changedNick = ''
            alert(res.data.message)
          } else {
            this.userNick = this.changedNick
            this.isNickInputOpen = false
            this.$emit('changeNick',this.userNick)
          }
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
    axios.get(`${SERVER_URL}/user/profile/${this.userNo}`, this.setToken())
      .then((res) => {
          this.userNick = res.data.data.userNick
          
      })
      .catch((err) => {
          console.log(err)
      })  
  }
}
</script>

<style scoped>
#profile-settings {
  position:relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 1920px;
  height: 969px;
  overflow:hidden;
  background-color: rgb(250, 246, 240);
  font-family: font1;
}
.blackboard {
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: hidden;
  width: 30%;
  height: 78%;
  top: 16%;
  padding: 35px;
  background-color: #3F9F47;;
  border-top: 30px solid #d69960;
  border-bottom: 30px solid #d69960;
  border-left: 30px solid #A87A4F;
  border-right: 30px solid #A87A4F;
}
.setting-title {
  position: relative;
  display: flex;
  justify-content: space-between;
  font-size: 40px;
  margin-top: 0;
  /* border-bottom: 1px lightgray solid; */
}
.settings-content {
  position: relative;
  display: flex;
  flex-direction: column;
  background-color: white;
  border-radius: 20px;
  /* text-align: left; */
  width: 100%;
  /* border: 3px solid red; */
  font-size: 25px;
  height: 100%;
  /* top: 17%; */
}
.settings-content > p{
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
  margin-top: 25px;
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
.Pin {
  position: relative;
  width: 25px;
  height: 25px;
  background-color: black;
  border-radius: 20px;
}

@media screen and (max-width: 501px) {
  #profile-settings {
    width: 100vw;
    height: 100vh;
  }
  .blackboard {
    top: 14%;
    width: 75%;
    height: 67%;
    padding: 15px;
    border-top: 15px solid #d69960;
    border-bottom: 15px solid #d69960;
    border-left: 15px solid #A87A4F;
    border-right: 15px solid #A87A4F;
  }
  .setting-title {
    font-size: 30px;
    /* padding-top: 5px; */
  }
  .Pin {
    width: 20px;
    height: 20px;
  }
  .nick-container {
    margin-top: 0;
  }
  .settings-content {
    font-size: 20px;
  }
  .settings-content p {
    margin: 15px 0;
  }
}
</style>