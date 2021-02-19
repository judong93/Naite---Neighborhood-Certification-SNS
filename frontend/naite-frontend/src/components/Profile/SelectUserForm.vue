<template>
  <div id="SelectUserForm">
    <div class="join-users">
      <div v-for="(user,idx) in userJoinList" :key='idx' class="join-user-container">
        <div @click="selectJoinUser(user)" class="join-user">
          <div class="join-user-nick-container">
            <i class="far fa-user-circle fa-2x"></i><span class="join-user-nick">{{user}}</span>
          </div>
          <i class="fas fa-chevron-right fa-2x"></i>
        </div>
      </div>
    </div>    
    <button @click="deleteMarketPosting" class="setting-btn delete-market-button">글 삭제</button>
  </div>
</template>

<script>
import axios from 'axios'

const SERVER_URL = 'https://i4a402.p.ssafy.io/api'

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
    boardNo:[String,Number],
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
    selectJoinUser: function (userNick) {
      this.$emit('selectJoinUser', userNick)
    },
    deleteMarketPosting: async function () { 
      const config = this.setToken()
      const result = confirm("정말 삭제하시겠습니까?")
      if (result) {
        axios.put(`${SERVER_URL}/board/delete/${this.boardNo}`, {}, config)
          .then(() => {
            alert('해당 게시물이 삭제되었습니다.')
            this.$emit('deleteMarket')
          })
          .catch((err) => {
            console.log(err)
            alert('로그아웃 되었습니다. 다시 로그인 진행해주세요!')
            localStorage.removeItem('jwt')
            this.$router.push({name:'Sign'})
          })        
      }
    },
  }
}
</script>

<style>
  #SelectUserForm {
    position: relative;
    height: 75%;
  }
  .join-users {
    position: relative;
    height: 70%;
  }
  .join-user-container {
    display: flex;
    overflow: auto;
    height: 60px;
  }

  .join-user {
    position: relative;
    display: flex;
    justify-content: left;
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
  .join-user-nick-container {
    display: flex;
    width: 90%;
  }
  .join-user-nick {
    font-size: 25px;
    left: 10%;
    margin-left: 5%;
  }
  .delete-market-button {
    height: 12%;
  }
</style>