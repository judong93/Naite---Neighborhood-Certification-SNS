<template>
  <div id="signup">
        <h2>signup</h2>
        
        <div class="signupform">
            <div class="signupleftdiv">
                <label for="#">ID</label><br>
                <input type="text"><br>
                <label for="#">PASSWORD</label><br>
                <input type="text"><br>
                <label for="#">PASSWORDCOMFRIMATION</label><br>
                <input type="text"><br>                
            </div>
            <div class="signuprightdiv">
                <label for="#">NAME</label><br>
                <input type="text"><br>                
                <label for="#">EMAIL</label><br>
                <input type="text"><br>            
                
                <span style='font-size:50px'>test location api 추가 예정</span><br>

                <label for="#">LOCATION</label><br>
                <input type="text" v-model='result.bname' readonly/><br>                
                
            </div>
        </div>

        <Location :searchLocation='searchLocation' @selectAddress = 'selectAddress'/>
        <button class="test" @click='test'>test_location_btn</button>
        


        <button @click='toLogin'>test: back to login or complete signup</button>
  </div>
</template>

<script>
import Location from '@/components/Location'


export default {
    name:'Signup',
    components:{
        Location,                
    },
    data: function() {
        return {
            searchLocation:false,
            result:'',
        }
    },
    methods:{
        toLogin:function(){
            this.$emit('changeLogin')
        },
        test:function(){
            if (this.searchLocation) {
                this.searchLocation = false
            } else {
                this.searchLocation=true
            }
        },
        selectAddress:function(result) {
            this.searchLocation = false
            this.result = result
        }
  
      
    },
    computed: {

    },
    props:{
        toSignup:Boolean,
    },
    watch:{
        toSignup:function(){
            const showSignup = document.getElementById('signup')
            if (this.toSignup){
                showSignup.style.right = '0%'
            } else {
                showSignup.style.right = '-100%'
            }
        }
    }
}
</script>



<style>
#signup {
    position: absolute;
    height: 100%;
    background-color:#ffffff;
    right:-100%;
    width:80%;      
    transition: 0.1s;  
}

#signup::before{
    position:absolute;
    content:'';
    background-color: rgb(129, 129, 238);
    transform:skewX(-5deg);
    width:10%;
    height: 100%;
    left:-5%;
}

.signupform {
    display:flex;
    justify-content: space-around;
}

.signupleftdiv{
    background-color: rgb(235, 255, 203);
    width: 40%;
}
.signuprightdiv{
    background-color: rgb(255, 217, 217);
    width: 40%;
    

}



</style>
