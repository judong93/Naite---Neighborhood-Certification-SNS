<template>
  <form action="#" class='pw-changing-form'>
    <label for="now-pw">현재 비밀번호</label><br>
    <input type="password" id='now-pw' 
    v-model="nowPw" @keypress.enter="changePw" @keypress.space='checkSpace'><br>
    <label for="new-pw">새 비밀번호</label><br>
    <input type="password" id='new-pw' 
    v-model="newPw" @keypress.enter="changePw" @keypress.space='checkSpace' @input='checkPw'
    placeholder="8자이상, 영어/숫자/특수문자를 한개 이상 사용해주세요"><br>
    <div class="pw-message" :class='{fontRed:!pwCheck,fontBlue:pwCheck}'>{{pwMessage}}</div>
    <label for="new-pw-check">새 비밀번호 확인</label><br>
    <input type="password" id='new-pw-check' 
    v-model="newPwConfirm" @keypress.enter="changePw" @keypress.space='checkSpace' @input='checkPwConfirm'><br>
    <div class="pw-confirm-message" :class='{fontRed:!pwCheckConfirm,fontBlue:pwCheckConfirm}'>{{pwConfirmMessage}}</div>
    <button class="setting-btn" @click="changePw">확인</button>
  </form>
</template>

<script>
import axios from 'axios'
var check_num = /[0-9]/;
var check_eng = /[a-zA-Z]/;
var check_spc = /[~!@#$%^&*()_+./,';|<>?:{}"-]/;

const SERVER_URL = 'https://i4a402.p.ssafy.io/api'

export default {
  name: 'PwChangeForm',
  data: function () {
    return {
      nowPw: '',
      newPw: '',
      newPwConfirm: '',
      pwCheck: false,
      pwMessage: '',
      pwCheckConfirm: false,
      pwConfirmMessage: '',
    }
  },
  methods: {
    checkSpace:function () {
      event.returnValue=false;
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
    changePw: function () {
      if (this.newPw!==this.newPwConfirm) {
        alert('비밀번호가 일치하지 않습니다!')
      } else {
        const config = this.setToken()
        axios.put(`${SERVER_URL}/user/profile/password`, {currentPw:this.nowPw, newPw: this.newPw}, config)
        .then((res) => {
          alert(res.data.message)
          this.nowPw = '',
          this.newPw = '',
          this.newPwConfirm = ''
          if (res.data.message==='비밀번호 재설정 완료') {
            setTimeout(() => {
            this.$emit('pwChangingCompleted')
          }, 400);
          }

        })
        .catch((err) => {
          console.log(err)
        })
      }  
    },
      checkPw:function(event){
        this.newPw = event.target.value
        if (!check_eng.test(this.newPw) || !check_num.test(this.newPw) || !check_spc.test(this.newPw)){
            this.pwMessage = '특수문자/영어/숫자를 모두 입력해주세요.'
            this.pwCheck = false
        } else if(this.newPw.length <8){
            this.pwMessage = '8자 이상 입력해주세요!'
            this.pwCheck = false
        } else {
            this.pwMessage = '확인되었습니다. 변경을 진행해주세요'
            this.pwCheck = true
        }
    },
    checkPwConfirm:function(event){
        this.newPwConfirm=event.target.value
        if (this.newPwConfirm !== this.newPw){
            this.pwConfirmMessage = '비밀번호를 일치시켜주세요'
            this.pwCheckConfirm = false
        } else if (!this.pwCheck){
            this.pwConfirmMessage = '비밀번호 양식을 맞춰주세요'
            this.pwCheckConfirm = false
        }  else {
            this.pwCheckConfirm = true
            this.pwConfirmMessage = '비밀번호가 일치합니다. 변경을 진행해주세요'
        }
    },
  }
}
</script>

<style>
#new-pw::placeholder{
  color: black;
  font-size: 50%  ;
}
.pw-changing-form input {
  width: 73%;
}
.pw-message {
  position: absolute;
  top: 46.5%;
  height: 10%;
  left: 16%;
  font-size: 80%;
  text-align: center;
  width: 70%;
}
.pw-confirm-message {
  position: absolute;
  top: 59.5%;
  height: 10%;
  left: 15%;
  font-size: 80%;
  text-align: center;
  width: 70%;
}
.fontRed {
    color:red
}

.fontBlue {
    color:rgb(0, 255, 0);
}
@media screen and (max-width: 501px) {
.pw-message {
  position: absolute;
  top: 49.5%;
  height: 10%;
  left: 15%;
  font-size: 80%;
}
.pw-confirm-message {
  position: absolute;
  top: 64.5%;
  height: 10%;
  left: 15%;
  font-size: 80%;
}
}
</style>