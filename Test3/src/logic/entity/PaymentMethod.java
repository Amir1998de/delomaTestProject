package logic.entity;

public enum PaymentMethod
{
	TRANSFER(new String[]{"/icons/paypal-icon1.png", "/icons/paypal-icon2.png", "/icons/paypal-icon3.png"}),
	
	PAYPAL(new String[]{"https://cdn-icons-png.flaticon.com/512/174/174861.png",
						"https://img.freepik.com/freie-ikonen/paypal_318-674245.jpg", 
						"https://upload.wikimedia.org/wikipedia/commons/thumb/b/b5/PayPal.svg/2560px-PayPal.svg.png"}),
	
	DEBIT(new String[]{"https://icon-library.com/images/debit-icon/debit-icon-1.jpg",
					   "https://www.svgrepo.com/show/174620/direct-debit-logo.svg",
					   "https://static-00.iconduck.com/assets.00/direct-debit-icon-2048x663-sxdx2kn4.png"});
	
	private final String[] iconPaths;
	
    PaymentMethod( String[] iconPaths) {
        
        this.iconPaths = iconPaths;
    }
    
    public String[] getIconPaths() {
        return iconPaths;
    }

}
