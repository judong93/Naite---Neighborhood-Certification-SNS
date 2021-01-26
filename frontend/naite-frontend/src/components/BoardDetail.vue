<template>
    <div id="boarddetail">

    </div>
    
</template>
<script>



export default {
    name:'BoardDetail',
    components:{
                
    },
    data: function() {
        return {

        }
    },
    props:{
        onDetail:Boolean,
    },
    methods:{

        
    },
    computed: {

    },
    watch:{
        onDetail: function(){
            const detailWindow = document.getElementById('boarddetail')
            if (this.onDetail){
                
                detailWindow.style.right='10%'
            } else {
                detailWindow.style.right='-100%'
            }
        }
    },

    
}
</script>

<style>
#boarddetail {
    position:absolute;
    width: 68%;
    height: 90%;
    background-color: rgba(236, 189, 189,0.5);
    top: 10%;
    right:-100%;
    
    transition:0.3s;
    
}
</style>