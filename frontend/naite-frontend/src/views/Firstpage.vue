<template>
  <div id="Firstpage">
    <span></span>
    <button class="firstpage-to-login"> start</button>



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
    },
    computed: {

    },
    
    
    created:{
        
    }
}
</script>



<style>
#Firstpage {
    position: relative;
    height: 100vh;
    background: url('../assets/firstpage.jpg') no-repeat center center;
    background-size: 100% 100%;
}


.firstpage-to-login{
    position: absolute;
    top: 80%;
    left: 50%;
    transform: translate(-50%, -50%);
    border-radius: 10px;
    cursor: pointer;

}

</style>
