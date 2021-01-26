<template>
  <div id="Firstpage">
    <div class="welcome-text">
        <span>자취생들을 위한 커뮤니티</span><br>
        <span>나의 이웃 테두리: 나이테</span>
    </div>
    <button class="start-btn" @click='go_to_sign'>start</button>



  </div>
</template>

<script>
export default {
    name:'Firstpage',
    components:{
                
    },
    data: function() {
        return {
        }
    },
    methods:{
        go_to_sign: function(){
            this.$router.push({name:'Sign'})
        }
    },
    computed: {

    },

}
</script>



<style>
#Firstpage {
    position: relative;
    height:100vh;
    width: 100vw;
    background: url('../assets/firstpage.jpg') no-repeat center center;
    background-size: 100% 100%;
}


.start-btn{
    position: absolute;
    top: 80%;
    left: 50%;
    transform: translate(-50%, -50%);
    border-radius: 1rem;
    cursor: pointer;
    width: 10%;
    height: 5%;
    font-size: 2em;

}

.welcome-text {
    position: absolute;
    top: 10vh;
    left: 10vw;
    font-size: 3rem;
    text-align: left;
}

</style>
