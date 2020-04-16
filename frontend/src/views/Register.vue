<template>
  <div>
    <div id="register" class="text-center">
      <form class="form-register" @submit.prevent="register">
        <h1 class="h3 mb-3 font-weight-normal">Create {{$route.params.role}} account</h1>
        <div class="alert alert-danger" role="alert" v-if="registrationErrors">
          There were problems registering user.
          <ul>
            <li v-for='regerror in regErrors' :key="regerror">{{regerror}}</li>
          </ul>
          For technical support call Trent Corrill at (513) 555-1212.
        </div>
        <label for="username" class="sr-only">Username</label>
        <b-form-input
          type="text"
          id="username"
          class="form-control"
          placeholder="Username"
          v-model="user.username"
          required
          autofocus
        /> 
        <label for="password" class="sr-only">Password</label>
        <b-form-input
          type="password"
          id="password"
          class="form-control"
          placeholder="Password"
          v-model="user.password"
          required
        /> 
        <b-form-input
          type="password"
          id="confirmPassword"
          class="form-control"
          placeholder="Confirm Password"
          v-model="user.confirmPassword"
          required
        />
        <!-- <router-link :to="{ name: 'login' }">
          Have an account?
        </router-link> -->
        <b-button class="btn btn-lg btn-primary btn-block" type="submit">
          Create {{$route.params.role}} account
        </b-button>
      </form>
    </div>
    <div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'register',
   props: {
      currentUser: Object
    },
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'parent',
        familyId: ''
      },
      registrationErrors: false,
      regErrors:  []
    };
  },
  methods: {
    register() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/register`, {
        method: 'POST',
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(this.user),
      })

        .then((response) => {
          if (response.ok) {
            this.registrationErrors = false;
            return (response.json());
          } else {
            this.registrationErrors = true; 
            throw 'Registration returned: ' + response.status;
          }
        })
        .then ((parsedData) => {
              
              if (parsedData.success) {
                this.registrationErrors = false;
                this.$router.push({ path: '/accounts'})
              } else {
                this.registrationErrors = true;
                this.regErrors = parsedData.errors;
              }
            
        })
        .catch((err) => console.log(err));
    },
  },
    created() {
    this.user.familyId = this.currentUser.familyId;
    let rolePassed = this.$route.params.role;
          console.log("rolePassed:" + rolePassed)
    if (rolePassed ){
        this.user.role = rolePassed;
    } else {
        this.user.role = 'parent';
    }
  }
}
</script>

<style scoped>
  html,body {
      height: 100%;
      background-color: #f5f5f5;
    }

  #register {
    height: 100%;
    display: -ms-flexbox;
    display: flex;
    -ms-flex-align: center;
    align-items: center;
    padding-top: 40px;
    padding-bottom: 40px;
    
    font-family: 'Roboto Condensed', sans-serif;
  }

  form {
    text-align: center;
  }

  .form-register {
    width: 100%;
    max-width: 330px;
    padding: 15px;
    margin: auto;
  }

  .form-register .form-control {
    position: relative;
    box-sizing: border-box;
    height: auto;
    padding: 10px;
    font-size: 16px;
  }
  .form-register .form-control:focus {
    z-index: 2;
  }
  .form-register input[type='text'] {
    margin-bottom: 10px;
    border-bottom-right-radius: 0;
    border-bottom-left-radius: 0;
  }
  .form-register input[type='password'] {
    margin-bottom: 10px;
    border-top-left-radius: 0;
    border-top-right-radius: 0;
  }
.alert{
    font-size: 12px;
    text-align: left;
  }
</style>
