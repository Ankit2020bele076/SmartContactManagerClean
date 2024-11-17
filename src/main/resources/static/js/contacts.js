console.log("hiiiiiiiiiii")
const baseURL = "http://localhost:8080";

const viewContactModel = document.getElementById('contact_view_model');

// options with default values
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
  id: 'contact_view_model',
  override: true
};

const contactModal = new Modal(viewContactModel,options,instanceOptions);

function showContact(id){
    contactModal.show();
}
function closeContact(){
    contactModal.hide();
}
async function viewContactDetails(id){
    console.log(id);
    
    try {
        const data = await (await fetch(`${baseURL}/api/contact/${id}`)).json();
        console.log(data);
        document.querySelector('#contact_picture').src = data.picture || 'https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/Default_pfp.svg/2048px-Default_pfp.svg.png';
        document.getElementById('contact_name').innerHTML = data.name;
        document.getElementById('contact_email').innerHTML = data.email;
        document.getElementById('contact_phoneNumber').innerHTML = data.phoneNumber;
        document.getElementById('contact_address').innerHTML = data.address;
        document.getElementById('contact_description').innerHTML = data.description;
        document.querySelector('#contact_websiteLink').href = data.websiteLink || '#';
        document.getElementById('contact_websiteLink').innerHTML = data.websiteLink || 'No website available';
        document.querySelector('#contact_linkedinLink').href = data.linkedInLink || '#';
        document.getElementById('contact_linkedinLink').innerHTML = data.linkedInLink || 'No linkedIn available';
        if (data.favorite) {
            document.getElementById('favorite_star').classList.remove('hidden');
        } else {
            document.getElementById('favorite_star').classList.add('hidden');
        }
        showContact();
    } catch (error) {
        console.log("Error: ",error);
    }
}

async function deleteContact(id){
    const swalWithTailwindButtons = Swal.mixin({ 
        customClass: {
          confirmButton: "m-2 bg-green-500 text-white font-bold py-2 px-4 rounded hover:bg-green-600 focus:outline-none",
          cancelButton: "bg-red-500 text-white font-bold py-2 px-4 rounded hover:bg-red-600 focus:outline-none"
        },
        buttonsStyling: false
      });
      
      swalWithTailwindButtons.fire({
        title: "Are you sure?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Yes, delete it!",
        cancelButtonText: "No, cancel!",
        reverseButtons: true
      }).then(async (result) => {
        if (result.isConfirmed) {
          swalWithTailwindButtons.fire({
            title: "Deleted!",
            text: "Your file has been deleted.",
            icon: "success"
          });
          const url = `${baseURL}/user/contacts/delete/${id}`;
          window.location.replace(url);
        } else if (
          result.dismiss === Swal.DismissReason.cancel
        ) {
          swalWithTailwindButtons.fire({
            title: "Cancelled",
            text: "Your file is safe :)",
            icon: "error"
          });
        }
      });
}