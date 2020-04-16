<template>
  <div>
     <div class="alert alert-danger" role="alert" v-if="currentUser">
          You are already assigned to a family. You cannot register a family with this account.
          To register a new family you will need to signout and create a new family.    
    </div>
    <div id="familyRegister" class="text-center" v-if="!currentUser">
      <form class="form-register" @submit.prevent="registerfamily">
        <h1 class="h3 mb-3 font-weight-normal">Register Family</h1>
        <div class="alert alert-danger" role="alert" v-if="familyAddErrors">
          There were problems creating this family.
          For technical support call Trent Corrill at (513) 555-1212.
        </div>
        <label for="familyname" class="sr-only">Family Name</label>
        <b-form-input
        type="text"
        id="familyname"
        class="form-control"
        placeholder="enter family name"
        v-model="family.familyname"
        required
        autofocus
        /> 
       <b-button class="btn btn-lg btn-primary btn-block"  v-show="family.id === 0"  type="submit">
          Register family
        </b-button>
      </form>
    </div>
    <div id="register" class="text-center" v-if="family.id != 0">
      <form class="form-register" @submit.prevent="register">
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
        <b-button class="btn btn-lg btn-primary btn-block" type="submit">
          Create Parent account
        </b-button>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'registerFamily',
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
        familyId: null
      },
      registrationErrors: false,
      familyAddErrors: false,
      family: {
          "id": 0,        
         "familyname": ''
        },
        familyerrors:  [],
        regErrors:  []
    };
  },
  methods: {
    registerfamily() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/families/`, {
        method: 'POST',
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(this.family),
      })
        .then((response) => {
          if (response.ok) {
            return (response.json());
          } else {
            this.familyAddErrors = true;
            throw 'Family add returned: ' + response.status;
          }
        })
        .then ((parsedData) => {
                this.family = parsedData;
                this.user.familyId = this.family.id;
              }
        )
        .catch((err) => console.log(err));
    },
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
                this.$router.push({ path: '/login', query: { registration: 'success' } });
              } else {
                this.registrationErrors = true;
                this.regErrors = parsedData.errors;
              }
            
        })
        .catch((err) => console.log(err));


    }
  }
};
</script>

<style scoped>
  html,body {
      height: 100%;
      background-color: #f5f5f5;
    }

  #familyRegister {
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