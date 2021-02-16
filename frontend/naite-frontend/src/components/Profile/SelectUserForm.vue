<template>
  <div id="SelectUserForm">
    <div v-for="(user,idx) in userJoinList" :key='idx' class="join-users">
      <div @click="selectJoinUser(user)" class="join-user">
        <i class="far fa-user-circle fa-2x"></i><span class="join-user-nick">{{user}}</span>
        <i class="fas fa-chevron-right fa-2x"></i>
      </div>
    </div>
  </div>
</template>

<script>
// import axios from 'axios'

// const SERVER_URL = 'https://i4a402.p.ssafy.io/api'

export default {
  name: 'SelectUserForm',
  data: function () {
    return {
      nowPw: '',
      newPw: '',
      newPwConfirm: '',
    }
  },
  props: {
    userJoinList: Array,
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
    selectJoinUser: function (userNick) {
      this.$emit('selectJoinUser', userNick)
    }
  }
}
</script>

<style>
  .join-users {
    display: flex;
    overflow: auto;
    height: 300px;
  }

  .join-user {
    display: flex;
    justify-content: space-between;
    border: 2px lightgray solid;
    border-left: none;
    border-right: none;
    margin-bottom: 3px;
    align-items: center;
    height: 55px;
    width: 100%;
    padding: 0 7%;
    cursor: pointer;
  }
  .join-user:hover {
    opacity: 0.5;
  }
  .join-user-nick {
    font-size: 25px;
    margin-left: 0;
    margin-right: 60%;
  }
</style>