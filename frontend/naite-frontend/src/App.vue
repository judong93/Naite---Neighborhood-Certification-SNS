<template>
  <div id="app">
    <Navbar v-if='signNav' />
    <router-view/>
    <Message v-if='signNav' />
  </div>
</template>
<script>
import Navbar from '@/components/Basic/Navbar'
import Message from '@/components/Basic/Message'
import axios from 'axios'

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
    }
  },
  methods:{
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
            alert('로그인 만료 재로그인 진행해주세요')
            this.$router.push({name:'Sign'})
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

</style>
