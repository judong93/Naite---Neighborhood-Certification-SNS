<template>
  <div id="profile-img-changing-form">
    <form action="#">
      <input type="file" class="profile-img-changing-input">
      <button class="setting-btn">프로필 이미지 변경</button>
    </form>
  </div>
</template>

<script>
// import axios from 'axios'

// const SERVER_URL = 'https://i4a402.p.ssafy.io/api'

export default {
  name: 'ProfileImgChangingForm',
  data: function () {
    return {
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
  }
}
</script>

<style>
  #profile-img-changing-form {

  }
  .profile-img-changing-input {
    width: 70%;
  }
  #profile-img-changing-form button {
    
  }
</style>