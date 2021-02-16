<template>
    <div id='search'>
        <input type="text" @click="changeState" v-model='searchContent' @keypress.enter="enterSearch">
        <div>
            {{apiData}}
        </div>
    </div>
</template>
<script>
import axios from 'axios'

const SERVER_URL = 'https://i4a402.p.ssafy.io/api'


export default {
    name:'Search',
    data:function(){
        return {
            clicked:false,
            searchContent:'',
            apiData:'',
        }
    },
    created(){
        setTimeout(() => {
            const searchInput = document.querySelector('#search > input')
            searchInput.style.display='initial'
        }, 500);
    },
    methods:{
        changeState:function(){
            this.clicked = true
        },
        enterSearch:function(){
            axios.get(`${SERVER_URL}/board/list/title/${this.searchContent}`,this.setToken())
                .then(res => {
                    this.apiData = res.data
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
    watch:{
        clicked:function(){
            const searchInput = document.querySelector('#search > input')
            if(this.clicked) {
                searchInput.style.top ='15%'
            } else {
                searchInput.style.top ='40%'
            }
        }
    }
}
</script>
<style>

#search {
  position:relative;
  height: 100vh;   
  width:100vw;  
  overflow:auto;
  background-color:  rgb(250, 246, 240);
}

#search > input {
    position:fixed;
    top:40%;
    left:50%;
    width: 700px;
    height: 50px;
    background-color: #A87A4F;
    transform: translate(-50%,-50%);
    z-index: 100;
    border:none;
    border-radius: 10px;
    outline: none;
    font-family: font1;
    color:white;
    display: none;
    transition: 0.3s;
}


#search > input+div {
    position:relative;
    top:30%;
    color:black;

}
</style>