<template>
  <div id="app">
    <Navbar v-if='signNav' />
    <router-view @sendMessageDirect='sendMessageDirect'/>
    <Message v-if='signNav' :directMessageRoomNo='directMessageRoomNo' />
  </div>
</template>
<script>
import Navbar from '@/components/Basic/Navbar'
import Message from '@/components/Basic/Message'
import axios from 'axios'
import jwt_decode from 'jwt-decode'

const SERVER_URL = 'https://i4a402.p.ssafy.io/api'

export default {
  name:'app',
  components:{
    Navbar,
    Message,
  },
  data: function(){
    return {
      signNav: false,
      mobileMessageState:false,
      directMessageRoomNo:'',
    }
  },
  methods:{
    sendMessageDirect:function(roomNo,userNick){
      this.directMessageRoomNo = {
        'roomNo':roomNo,
        'userNick':userNick
      }

    },
    showing:function(){
      const jwt = localStorage.getItem('jwt')
      if (jwt) {
        this.signNav = true
      } else if (!jwt) {
        this.signNav = false
      }
    },
    token:function(){
      const jwt = localStorage.getItem('jwt')
      if (jwt&&window.location.pathname==='/sign') {
        this.$router.push({name:'MainBoard'})
      } else if (jwt&&window.location.pathname==='/'){
        this.$router.push({name:'MainBoard'})
      } else if (!jwt && window.location.pathname !=='/sign') {
        this.$router.push({name:'Sign'})
      }
    },
    usingToken:function(){
      axios.get(`${SERVER_URL}/user/token`,this.setToken())
        .then(res=>{          
          if(res.data.response!=='success' && localStorage.getItem('jwt')){
            localStorage.removeItem('jwt')

            if(this.$store.state.loginState.autologin) {
              const params = {
                'userId': this.$store.state.autoLogin.userId,
                'userPw':this.$store.state.autoLogin.userPw
              }
              axios.post(`${SERVER_URL}/user/sign/signin`,params)
                    .then(res=>{
                        const token = res.data['auth-token']
                        const decoded = jwt_decode(token)
                        this.$router.push({name:'Main'})
                        this.$store.dispatch('saveuserinfo',decoded)                        
                        localStorage.setItem('jwt',res.data['auth-token'])
              })
            } else {
              alert('로그인 만료 재로그인 진행해주세요')
              this.$router.push({name:'Sign'})
            }

            
          }
        })
        .catch(err=>{
          console.log(err)
        })
    },
    setToken:function(){
      const token=localStorage.getItem('jwt')      
      const config = {
          headers: {
          'auth-token':`${token}`
          }
        }        
      return config 
    }
  },
  updated:function(){
    this.token()
    this.showing()
    if (localStorage.getItem('jwt')){
      this.usingToken()
    }
  },
  created:function(){
    this.token()
    this.showing()
    if (localStorage.getItem('jwt')){
      this.usingToken()
    }

  }
}

</script>
<style>
@font-face {
    font-family: font1;
    src: url(./fonts/titleli.ttf) format('truetype');
}


#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

 
@media screen and (max-width: 501px) {
    #app {
        width: 100vw;
        height: 100vh;
        overflow: auto;
    }
}

</style>
