<template>
  <form action="#" class='pw-changing-form'>
    <label for="now-pw">현재 비밀번호</label><br>
    <input type="password" id='now-pw' 
    v-model="nowPw" @keypress.enter="changePw" @keypress.space='checkSpace'><br>
    <label for="new-pw">새 비밀번호</label><br>
    <input type="password" id='new-pw' 
    v-model="newPw" @keypress.enter="changePw" @keypress.space='checkSpace'><br>
    <label for="new-pw-check">새 비밀번호 확인</label><br>
    <input type="password" id='new-pw-check' 
    v-model="newPwConfirm" @keypress.enter="changePw" @keypress.space='checkSpace'><br>
    <button class="setting-btn" @click="changePw">확인</button>
  </form>
</template>

<script>
import axios from 'axios'

const SERVER_URL = 'https://i4a402.p.ssafy.io/api'

export default {
  name: 'PwChangeForm',
  data: function () {
    return {
      nowPw: '',
      newPw: '',
      newPwConfirm: '',
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
      console.log(config)
      return config
    },
    changePw: function () {
      if (this.newPw!==this.newPwConfirm) {
        alert('비밀번호가 일치하지 않습니다!')
      } else {
        const config = this.setToken()
        axios.put(`${SERVER_URL}/user/profile/password`, {currentPw:this.nowPw, newPw: this.newPw}, config)
        .then((res) => {
          console.log(res)
          alert('비밀번호가 변경되었습니다!')
          this.$emit('pwChangingCompleted')
        })
        .catch((err) => {
          console.log(err)
        })
      }  
    }
  }
}
</script>

<style>

</style>